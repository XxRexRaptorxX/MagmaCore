#!/usr/bin/env node

const fs = require('fs').promises;
const path = require('path');
const simpleGit = require('simple-git');
const conventionalCommitsParser = require('conventional-commits-parser');
const semver = require('semver');

const git = simpleGit();

class ChangelogGenerator {
  constructor() {
    this.changelogPath = 'LATEST_CHANGELOG.md';
    this.gradlePropsPath = 'gradle.properties';
    this.commitCategories = {
      feat: '✨ Features',
      fix: '🐛 Bug Fixes',
      docs: '📚 Documentation',
      style: '💎 Style',
      refactor: '📦 Code Refactoring',
      perf: '🚀 Performance Improvements',
      test: '🚨 Tests',
      build: '🛠️ Build System',
      ci: '⚙️ Continuous Integration',
      chore: '♻️ Chores',
      revert: '🗑️ Reverts'
    };
  }

  async run() {
    try {
      console.log('🚀 Starting changelog generation...');

      // Read current version from gradle.properties
      const currentVersion = await this.readVersionFromGradle();
      console.log(`📋 Current version: ${currentVersion.mod_version} for MC ${currentVersion.game_versions}`);

      // Get latest tag version
      const latestTag = await this.getLatestVersionTag();
      console.log(`🏷️  Latest tag: ${latestTag || 'none'}`);

      // Check if we need to create a new tag
      const shouldCreateTag = await this.shouldCreateNewTag(currentVersion.mod_version, latestTag);

      if (shouldCreateTag) {
        console.log(`🆕 Creating new tag for version ${currentVersion.mod_version}`);
        await this.createVersionTag(currentVersion.mod_version);
      }

      // Get commits for current version
      const commits = await this.getCommitsForCurrentVersion(currentVersion.mod_version);

      if (commits.length === 0) {
        console.log('ℹ️  No new commits found for current version');
        return;
      }

      console.log(`📝 Found ${commits.length} commits to process`);

      // Parse and categorize commits
      const categorizedCommits = await this.categorizeCommits(commits);

      // Generate or update changelog
      await this.updateChangelog(currentVersion, categorizedCommits);

      // Commit and push changes if there are any
      await this.commitAndPushChanges(currentVersion.mod_version);

      console.log('✅ Changelog generation completed!');

    } catch (error) {
      console.error('❌ Error generating changelog:', error.message);
      process.exit(1);
    }
  }

  async readVersionFromGradle() {
    try {
      const content = await fs.readFile(this.gradlePropsPath, 'utf-8');
      const lines = content.split('\n');

      const version = {};

      for (const line of lines) {
        const trimmed = line.trim();
        if (trimmed.startsWith('mod_version=')) {
          version.mod_version = trimmed.split('=')[1].trim();
        }
        if (trimmed.startsWith('game_versions=')) {
          version.game_versions = trimmed.split('=')[1].trim();
        }
      }

      if (!version.mod_version || !version.game_versions) {
        throw new Error('Could not find mod_version or game_versions in gradle.properties');
      }

      return version;
    } catch (error) {
      throw new Error(`Failed to read gradle.properties: ${error.message}`);
    }
  }

  async getLatestVersionTag() {
    try {
      const tags = await git.tags();

      // Filter version tags (starting with 'v')
      const versionTags = tags.all
        .filter(tag => tag.startsWith('v'))
        .map(tag => tag.substring(1)) // Remove 'v' prefix
        .filter(version => semver.valid(version))
        .sort((a, b) => semver.rcompare(a, b)); // Sort descending

      return versionTags.length > 0 ? versionTags[0] : null;
    } catch (error) {
      console.log('⚠️  Could not get tags, assuming first version');
      return null;
    }
  }

  async shouldCreateNewTag(currentVersion, latestTag) {
    if (!latestTag) return true;

    // Compare versions using semver
    const isNewer = semver.gt(currentVersion, latestTag);
    return isNewer;
  }

  async createVersionTag(version) {
    try {
      const tagName = `v${version}`;
      await git.addAnnotatedTag(tagName, `Release version ${version}`);
      console.log(`✅ Created tag: ${tagName}`);
    } catch (error) {
      console.log(`⚠️  Could not create tag: ${error.message}`);
    }
  }

  async getCommitsForCurrentVersion(currentVersion) {
    try {
      // Get the latest tag for this version
      const tags = await git.tags();
      const currentVersionTag = `v${currentVersion}`;

      let fromRef;

      if (tags.all.includes(currentVersionTag)) {
        // If tag exists, get commits since the previous version tag
        const versionTags = tags.all
          .filter(tag => tag.startsWith('v'))
          .map(tag => tag.substring(1))
          .filter(version => semver.valid(version))
          .sort((a, b) => semver.rcompare(a, b));

        const currentIndex = versionTags.indexOf(currentVersion);
        fromRef = currentIndex < versionTags.length - 1 ? `v${versionTags[currentIndex + 1]}` : null;
      } else {
        // No tag yet, get all commits from the latest different version tag
        const otherVersionTags = tags.all
          .filter(tag => tag.startsWith('v'))
          .map(tag => tag.substring(1))
          .filter(version => semver.valid(version) && version !== currentVersion)
          .sort((a, b) => semver.rcompare(a, b));

        fromRef = otherVersionTags.length > 0 ? `v${otherVersionTags[0]}` : null;
      }

      // Get commits
      const logOptions = {
        from: fromRef || undefined,
        to: 'HEAD',
        format: {
          hash: '%H',
          date: '%ai',
          message: '%s',
          body: '%b',
          author_name: '%an',
          author_email: '%ae'
        }
      };

      const log = await git.log(logOptions);

      // Filter out merge commits and changelog commits
      return log.all.filter(commit => {
        const message = commit.message.toLowerCase();
        return !commit.message.startsWith('Merge') &&
               !message.includes('changelog') &&
               !message.includes('update latest_changelog') &&
               !message.startsWith('docs: update changelog');
      });

    } catch (error) {
      console.log(`⚠️  Error getting commits: ${error.message}`);
      return [];
    }
  }

  async categorizeCommits(commits) {
    const categorized = {};

    for (const commit of commits) {
      try {
        const parsed = conventionalCommitsParser.sync(commit.message);

        const type = parsed.type || 'chore';
        const scope = parsed.scope ? `(${parsed.scope})` : '';
        const description = parsed.subject || commit.message;
        const breaking = parsed.notes.some(note => note.title === 'BREAKING CHANGE');

        const category = this.commitCategories[type] || this.commitCategories.chore;

        if (!categorized[category]) {
          categorized[category] = [];
        }

        const formattedCommit = {
          description: `${description}${scope}`,
          hash: commit.hash.substring(0, 7),
          breaking,
          original: commit
        };

        categorized[category].push(formattedCommit);

      } catch (error) {
        // Fallback for non-conventional commits
        const category = this.commitCategories.chore;
        if (!categorized[category]) {
          categorized[category] = [];
        }

        categorized[category].push({
          description: commit.message,
          hash: commit.hash.substring(0, 7),
          breaking: false,
          original: commit
        });
      }
    }

    return categorized;
  }

  async updateChangelog(version, categorizedCommits) {
    const versionHeader = `# v${version.mod_version} for MC ${version.game_versions}`;

    // Check if changelog exists and if it's for the same version
    let existingContent = '';
    let shouldReset = true;

    try {
      existingContent = await fs.readFile(this.changelogPath, 'utf-8');
      const lines = existingContent.split('\n');

      // Check if first line matches current version
      if (lines.length > 0 && lines[0].trim() === versionHeader) {
        shouldReset = false;
        console.log('📄 Updating existing changelog for current version');
      } else {
        console.log('🆕 Creating new changelog for new version');
      }
    } catch (error) {
      console.log('📄 Creating new changelog file');
      shouldReset = true;
    }

    let newContent;

    if (shouldReset) {
      // Create fresh changelog
      newContent = this.generateFreshChangelog(versionHeader, categorizedCommits);
    } else {
      // Merge with existing changelog
      newContent = await this.mergeWithExistingChangelog(existingContent, categorizedCommits);
    }

    await fs.writeFile(this.changelogPath, newContent, 'utf-8');
    console.log(`✅ Updated ${this.changelogPath}`);
  }

  generateFreshChangelog(versionHeader, categorizedCommits) {
    let content = versionHeader + '\n\n';

    // Add categories in order
    const orderedCategories = [
      '✨ Features',
      '🐛 Bug Fixes',
      '🚀 Performance Improvements',
      '📦 Code Refactoring',
      '📚 Documentation',
      '🚨 Tests',
      '🛠️ Build System',
      '⚙️ Continuous Integration',
      '💎 Style',
      '♻️ Chores',
      '🗑️ Reverts'
    ];

    for (const category of orderedCategories) {
      if (categorizedCommits[category] && categorizedCommits[category].length > 0) {
        content += `## ${category}\n\n`;

        for (const commit of categorizedCommits[category]) {
          const breakingPrefix = commit.breaking ? '**BREAKING:** ' : '';
          content += `- ${breakingPrefix}${commit.description} ([${commit.hash}](../../commit/${commit.original.hash}))\n`;
        }

        content += '\n';
      }
    }

    return content.trim() + '\n';
  }

  async mergeWithExistingChangelog(existingContent, newCategorizedCommits) {
    const lines = existingContent.split('\n');
    const existingCommits = new Set();

    // Extract existing commit hashes to avoid duplicates
    for (const line of lines) {
      const hashMatch = line.match(/\[([a-f0-9]{7})\]/);
      if (hashMatch) {
        existingCommits.add(hashMatch[1]);
      }
    }

    // Filter out commits that already exist
    const filteredCommits = {};
    for (const [category, commits] of Object.entries(newCategorizedCommits)) {
      const newCommits = commits.filter(commit => !existingCommits.has(commit.hash));
      if (newCommits.length > 0) {
        filteredCommits[category] = newCommits;
      }
    }

    if (Object.keys(filteredCommits).length === 0) {
      console.log('ℹ️  No new commits to add');
      return existingContent;
    }

    // Parse existing content and merge
    let newContent = '';
    let currentCategory = null;
    let inContent = false;

    for (let i = 0; i < lines.length; i++) {
      const line = lines[i];

      if (line.startsWith('# ')) {
        newContent += line + '\n';
        inContent = true;
        continue;
      }

      if (line.startsWith('## ')) {
        currentCategory = line.substring(3);
        newContent += line + '\n';

        // Add new commits to this category
        if (filteredCommits[currentCategory]) {
          // First add existing commits for this category
          let j = i + 1;
          while (j < lines.length && lines[j].startsWith('- ')) {
            newContent += lines[j] + '\n';
            j++;
          }

          // Then add new commits
          for (const commit of filteredCommits[currentCategory]) {
            const breakingPrefix = commit.breaking ? '**BREAKING:** ' : '';
            newContent += `- ${breakingPrefix}${commit.description} ([${commit.hash}](../../commit/${commit.original.hash}))\n`;
          }

          // Skip the existing commits we already added
          i = j - 1;
          delete filteredCommits[currentCategory];
        }
        continue;
      }

      if (inContent) {
        newContent += line + '\n';
      }
    }

    // Add any remaining new categories
    for (const [category, commits] of Object.entries(filteredCommits)) {
      newContent += `\n## ${category}\n\n`;
      for (const commit of commits) {
        const breakingPrefix = commit.breaking ? '**BREAKING:** ' : '';
        newContent += `- ${breakingPrefix}${commit.description} ([${commit.hash}](../../commit/${commit.original.hash}))\n`;
      }
    }

    return newContent.trim() + '\n';
  }

  async commitAndPushChanges(version) {
    try {
      // Check if there are changes to commit
      const status = await git.status();

      if (status.files.length === 0) {
        console.log('ℹ️  No changes to commit');
        return;
      }

      // Check if LATEST_CHANGELOG.md is modified
      const changelogModified = status.files.some(file =>
        file.path === this.changelogPath &&
        (file.working_dir === 'M' || file.working_dir === '?')
      );

      if (changelogModified) {
        await git.add(this.changelogPath);
        await git.commit(`docs: update changelog for v${version}`, {
          '--no-verify': null // Skip hooks to prevent recursion
        });

        // Push commits and tags
        await git.push('origin', 'main');
        await git.pushTags('origin');

        console.log(`✅ Committed and pushed changelog changes for v${version}`);
      }

    } catch (error) {
      console.log(`⚠️  Could not commit/push changes: ${error.message}`);
    }
  }
}

// Run the generator
if (require.main === module) {
  const generator = new ChangelogGenerator();
  generator.run();
}