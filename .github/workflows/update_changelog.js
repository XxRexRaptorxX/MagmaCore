/**
 * update-changelog.js
 *
 * What this script does:
 * - Reads gradle.properties to get mod_version and game_versions
 * - Gathers commits in the range BEFORE..AFTER (from env)
 * - Parses commit subjects using conventional-commit convention (basic parsing with regex)
 * - Produces a markdown changelog fragment with "Features" first
 * - If the version header exists in CHANGELOG.MD, inserts new commits into that version:
 *     - It will attempt to place commits under matching subsections, else under "### Other commits"
 * - If the version header does NOT exist:
 *     - Overwrites LATEST_CHANGELOG.MD with the new-version content
 *     - Prepends the new-version content to CHANGELOG.MD (old content appended below)
 * - If files changed, commits and pushes changes back using provided GITHUB_TOKEN
 *
 * Notes:
 * - This implementation avoids external npm dependencies by using simple regex parsing.
 * - It treats "feat" specially (Features placed first).
 * - It groups other conventional types into named sections when possible; unknown types go into "Other commits".
 */

const { execSync } = require('child_process');
const fs = require('fs');
const path = require('path');

// Helper: safe exec returning string (stdout)
function exec(cmd, opts = {}) {
  try {
    return execSync(cmd, { encoding: 'utf8', stdio: ['pipe', 'pipe', 'pipe'], ...opts }).toString();
  } catch (e) {
    // return empty string on failure (we handle missing ranges later)
    return '';
  }
}

// Read env vars provided by workflow
const BEFORE = process.env.BEFORE || '';
const AFTER = process.env.AFTER || '';
const GITHUB_TOKEN = process.env.GITHUB_TOKEN || '';
const GITHUB_ACTOR = process.env.GITHUB_ACTOR || 'github-actions[bot]';
const GITHUB_REPOSITORY = process.env.GITHUB_REPOSITORY || '';
const GITHUB_REF = process.env.GITHUB_REF || 'refs/heads/main';

// Utility to escape regex
function reEscape(s) {
  return s.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

// 1) Read gradle.properties to get mod_version and game_versions
function readGradleProperties(filePath = 'gradle.properties') {
  const abs = path.resolve(filePath);
  if (!fs.existsSync(abs)) {
    throw new Error(`gradle.properties not found at ${abs}`);
  }
  const txt = fs.readFileSync(abs, 'utf8');
  const lines = txt.split(/\r?\n/);
  const obj = {};
  for (const l of lines) {
    const m = l.match(/^\s*([^=]+?)\s*=\s*(.+)\s*$/);
    if (m) {
      obj[m[1].trim()] = m[2].trim();
    }
  }
  return obj;
}

// 2) Get commits in range BEFORE..AFTER
function getCommits(before, after) {
  // If BEFORE is all zeros or empty, use just the single commit 'after' (initial push / force pushes)
  const zero = '0000000000000000000000000000000000000000';
  let range = '';
  if (!before || before === zero) {
    // just the single commit (use that commit only)
    range = after;
  } else {
    range = `${before}..${after}`;
  }

  // We will collect commits excluding merges (--no-merges), in reverse (oldest->newest)
  // Use a separator record format to parse easily
  const format = '%H%x1f%s%x1f%b%x1e'; // field sep: unit separator, record sep: record sep
  const cmd = `git log --no-merges --pretty=format:"${format}" ${range}`;
  const out = exec(cmd);
  if (!out) return [];
  // split records by record sep (\x1e)
  const rawRecords = out.split('\x1e').map(s => s.trim()).filter(Boolean);
  const commits = rawRecords.map(rec => {
    const parts = rec.split('\x1f');
    const hash = (parts[0] || '').trim();
    const subject = (parts[1] || '').trim();
    const body = (parts[2] || '').trim();
    return { hash, subject, body };
  });
  return commits;
}

// 3) Parse conventional commit subject into type/scope/description
function parseConventionalSubject(subject) {
  // typical patterns:
  // type(scope)!: description
  // type!: description
  // type(scope): description
  // type: description
  const re = /^(\w+)(?:\(([^)]+)\))?(!)?:\s*(.+)$/;
  const m = subject.match(re);
  if (m) {
    return {
      type: m[1].toLowerCase(),
      scope: m[2] || null,
      breaking: !!m[3],
      description: m[4].trim(),
    };
  } else {
    // fallback: unknown type, whole subject as description
    return {
      type: 'other',
      scope: null,
      breaking: false,
      description: subject.trim(),
    };
  }
}

// 4) Build changelog markdown for a set of commits
function buildChangelogMarkdown(versionHeader, commits) {
  // sections mapping: heading -> array of lines
  const sections = {};
  // ensure Features section exists but only show if not empty
  sections['Features'] = [];
  // known conventional types mapped to headings
  const typeHeadings = {
    feat: 'Features',
    fix: 'Fixes',
    docs: 'Documentation',
    chore: 'Chores',
    refactor: 'Refactor',
    perf: 'Performance',
    test: 'Tests',
    style: 'Style',
    build: 'Build',
    ci: 'CI',
  };
  // other commits go into 'Other commits'
  sections['Other commits'] = [];

  for (const c of commits) {
    const parsed = parseConventionalSubject(c.subject);
    const shortHash = c.hash ? c.hash.substring(0, 7) : '';
    // Build a nice bullet line: "- description (hash)"
    // If scope exists, include it in parentheses in the message
    const scopePart = parsed.scope ? `**${parsed.scope}**: ` : '';
    const breakingFlag = parsed.breaking ? ' **(BREAKING)**' : '';
    const line = `- ${scopePart}${parsed.description}${breakingFlag} (${shortHash})`;

    const heading = typeHeadings[parsed.type] || 'Other commits';
    if (!sections[heading]) sections[heading] = [];
    sections[heading].push(line);
  }

  // Compose markdown
  const lines = [];
  lines.push(`## ${versionHeader}`);
  // add a date (ISO) for the changelog entry
  lines.push(`\n_${new Date().toISOString().split('T')[0]}_\n`);
  // Features must appear first (if any)
  if (sections['Features'] && sections['Features'].length > 0) {
    lines.push(`### Features\n`);
    lines.push(...sections['Features']);
    lines.push('');
  }
  // then other headings (except Features and Other commits), maintain a preferred order for common types
  const preferredOrder = ['Fixes', 'Documentation', 'Performance', 'Refactor', 'Tests', 'Style', 'Build', 'CI', 'Chores'];
  for (const h of preferredOrder) {
    if (h === 'Features') continue;
    if (sections[h] && sections[h].length > 0) {
      lines.push(`### ${h}\n`);
      lines.push(...sections[h]);
      lines.push('');
    }
  }
  // Finally, include any headings that were not in preferredOrder and not Features
  for (const h of Object.keys(sections)) {
    if (h === 'Features') continue;
    if (preferredOrder.includes(h)) continue;
    if (h === 'Other commits') continue;
    if (sections[h] && sections[h].length > 0) {
      lines.push(`### ${h}\n`);
      lines.push(...sections[h]);
      lines.push('');
    }
  }
  // Other commits last (if any)
  if (sections['Other commits'] && sections['Other commits'].length > 0) {
    lines.push(`### Other commits\n`);
    lines.push(...sections['Other commits']);
    lines.push('');
  }

  return lines.join('\n');
}

// 5) Insert into existing CHANGELOG.MD if version exists, otherwise create new and update LATEST_CHANGELOG.MD
function updateFiles(versionHeader, newMarkdown) {
  const changelogPath = path.resolve('CHANGELOG.MD');
  const latestPath = path.resolve('LATEST_CHANGELOG.MD');

  const changelogExists = fs.existsSync(changelogPath);
  const changelogText = changelogExists ? fs.readFileSync(changelogPath, 'utf8') : '';

  // find if version header exists in CHANGELOG.MD
  // header pattern: starting line with "## v.<mod_version> for MC <game_versions>"
  const headerRe = new RegExp('^##\\s+' + reEscape(versionHeader) + '\\s*$', 'm');
  const headerMatch = changelogText.match(headerRe);

  if (headerMatch) {
    // Version exists: insert commits into that version's section.
    // Find the start index of that header, then find the end (next "## " header or EOF)
    const headerIndex = changelogText.search(headerRe);
    // find next "## " after headerIndex
    const rest = changelogText.slice(headerIndex);
    const nextHeaderRe = /^##\s+/m;
    // find position of next header by searching from second line onwards
    const nextHeaderMatch = rest.slice(4).search(nextHeaderRe); // skip "## " marker
    let sectionEndIndex;
    if (nextHeaderMatch === -1) {
      sectionEndIndex = changelogText.length;
    } else {
      sectionEndIndex = headerIndex + 4 + nextHeaderMatch;
    }
    const section = changelogText.slice(headerIndex, sectionEndIndex);
    // Look for "### Other commits" inside the section (case-insensitive)
    const otherRe = /###\s+Other\s+commits/i;
    if (otherRe.test(section)) {
      // append newMarkdown lines (only the lists) under Other commits
      // We'll append the list items from newMarkdown after the "### Other commits" header
      // Extract list lines from newMarkdown (everything after '### Other commits' if present, else entire body)
      // Simpler: extract bullet lines from newMarkdown
      const bulletLines = newMarkdown.split(/\r?\n/).filter(l => l.trim().startsWith('- '));
      if (bulletLines.length === 0) {
        console.log('No bullet lines to append.');
        return false;
      }
      // Replace section by inserting bullet lines before the end of section
      // Find insertion point: after the last occurrence of '### Other commits' in the section
      const lastOtherIndex = section.search(otherRe);
      const afterOtherHeaderIndex = (() => {
        const m = section.slice(lastOtherIndex).match(/###\s+Other\s+commits[^\n]*\n/i);
        if (m) {
          return lastOtherIndex + m[0].length;
        }
        return section.length;
      })();
      // insert bulletLines at afterOtherHeaderIndex
      const newSection = section.slice(0, afterOtherHeaderIndex) + '\n' + bulletLines.join('\n') + '\n' + section.slice(afterOtherHeaderIndex);
      const newChangelog = changelogText.slice(0, headerIndex) + newSection + changelogText.slice(sectionEndIndex);
      fs.writeFileSync(changelogPath, newChangelog, 'utf8');
      console.log(`Appended ${bulletLines.length} items into existing version under "Other commits".`);
      return true;
    } else {
      // No Other commits header: create it at end of the section and add bullets
      const bulletLines = newMarkdown.split(/\r?\n/).filter(l => l.trim().startsWith('- '));
      const insertion = '\n### Other commits\n\n' + bulletLines.join('\n') + '\n';
      const newSection = section + insertion;
      const newChangelog = changelogText.slice(0, headerIndex) + newSection + changelogText.slice(sectionEndIndex);
      fs.writeFileSync(changelogPath, newChangelog, 'utf8');
      console.log(`Created "Other commits" for existing version and added ${bulletLines.length} items.`);
      return true;
    }
  } else {
    // Version does not exist:
    // - Overwrite LATEST_CHANGELOG.MD with the new version content
    fs.writeFileSync(latestPath, newMarkdown + '\n', 'utf8');
    console.log(`Wrote new content to LATEST_CHANGELOG.MD`);

    // - Prepend new version to CHANGELOG.MD (new content first, then previous content)
    const prependContent = newMarkdown + '\n\n' + changelogText;
    fs.writeFileSync(changelogPath, prependContent, 'utf8');
    console.log(`Prepended new version to CHANGELOG.MD`);
    return true;
  }
}

// 6) Commit & push changes if any
function gitCommitAndPush() {
  try {
    // configure git user
    execSyncSafe('git config user.name "github-actions[bot]"');
    execSyncSafe('git config user.email "github-actions[bot]@users.noreply.github.com"');
    execSyncSafe('git add CHANGELOG.MD LATEST_CHANGELOG.MD');
    // commit; allow no-op if nothing to commit
    const commitResult = exec('git commit -m "chore(changelog): update changelogs [skip ci]" || true');
    if (!/nothing to commit/i.test(commitResult)) {
      // push using token. Build remote URL
      if (!GITHUB_TOKEN) {
        console.error('GITHUB_TOKEN unavailable; cannot push changes.');
        return;
      }
      // derive branch name from GITHUB_REF
      const branch = (GITHUB_REF || 'refs/heads/main').replace('refs/heads/', '');
      // push using https URL (token in URL). This will leave token in process output if printed; avoid printing the push command output.
      const remote = `https://${encodeURIComponent(GITHUB_ACTOR)}:${encodeURIComponent(GITHUB_TOKEN)}@github.com/${GITHUB_REPOSITORY}.git`;
      execSyncSafe(`git push ${remote} HEAD:${branch}`);
      console.log('Pushed changelog updates.');
    } else {
      console.log('No changes to commit.');
    }
  } catch (e) {
    console.error('Error during git commit/push:', e.message || e);
  }
}

function execSyncSafe(cmd) {
  try {
    return execSync(cmd, { stdio: 'pipe', encoding: 'utf8' });
  } catch (e) {
    // swallow error but print message
    console.error(`Command failed: ${cmd}\n${e.message || e}`);
    return '';
  }
}

// Main run
(async function main() {
  try {
    const gradle = readGradleProperties('gradle.properties');
    const mod_version = gradle['mod_version'] || gradle['version'] || null;
    const game_versions = gradle['game_versions'] || gradle['game_version'] || null;

    if (!mod_version || !game_versions) {
      throw new Error('Could not find mod_version and/or game_versions in gradle.properties. Expected keys: mod_version and game_versions');
    }

    const versionHeader = `v.${mod_version} for MC ${game_versions}`;
    console.log('Target version header:', versionHeader);

    // get commits in range
    const commits = getCommits(BEFORE, AFTER);
    if (!commits || commits.length === 0) {
      console.log('No commits found in the provided range. Exiting without changes.');
      return;
    }

    console.log(`Found ${commits.length} commits to process.`);

    // Build markdown for the commits
    const newMarkdown = buildChangelogMarkdown(versionHeader, commits);
    // Update files accordingly
    const changed = updateFiles(versionHeader, newMarkdown);

    if (changed) {
      gitCommitAndPush();
    } else {
      console.log('No changes written to files.');
    }
  } catch (err) {
    console.error('Fatal error:', err.message || err);
    process.exit(1);
  }
})();
