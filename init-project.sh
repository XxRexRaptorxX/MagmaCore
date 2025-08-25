#!/bin/bash

# Initialization Script
echo "🚀 Initializing Generator..."

# Check if we're in a git repository
if [ ! -d ".git" ]; then
    echo "❌ Error: Not in a git repository. Please run this from your mod's root directory."
    exit 1
fi

# Check if gradle.properties exists
if [ ! -f "gradle.properties" ]; then
    echo "❌ Error: gradle.properties not found. Please make sure you're in your mod's root directory."
    exit 1
fi

# Verify gradle.properties has required fields
if ! grep -q "mod_version=" gradle.properties || ! grep -q "game_versions=" gradle.properties; then
    echo "❌ Error: gradle.properties must contain 'mod_version=' and 'game_versions=' properties."
    echo "Example:"
    echo "mod_version=1.0.0"
    echo "game_versions=1.20.1"
    exit 1
fi

echo "✅ Found gradle.properties with required properties"

# Create .gitattributes entries if .gitattributes exists
if [ -f ".gitattributes" ]; then
    if ! grep -q "node_modules" .gitattributes; then
        echo "" >> .gitattributes
        echo "*.java text eol=lf" >> .gitattributes
        echo "*.gradle text eol=lf" >> .gitattributes
        echo "*.md text eol=lf" >> .gitattributes
        echo "*.yml text eol=lf" >> .gitattributes
        echo "*.yaml text eol=lf" >> .gitattributes
        echo "✅ Added required entries for Spotless to .gitattributes"
    fi
else
    cat > .gitattributes << 'EOF'
*.java text eol=lf
*.gradle text eol=lf
*.md text eol=lf
*.yml text eol=lf
*.yaml text eol=lf
EOF
    echo "✅ Created .gitattributes"
fi

# Create pre-commit githook
cat > .githooks/pre-commit << 'EOF'
#!/bin/sh
./gradlew updateChangelog
git add LATEST_CHANGELOG.md
EOF
echo "✅ Created .githooks/pre-commit"

# Create LATEST_CHANGELOG.md
if [ ! -f "LATEST_CHANGELOG.md" ]; then
    # try to restore from git HEAD
    if git show HEAD:./LATEST_CHANGELOG.md >/dev/null 2>&1; then
        git show HEAD:./LATEST_CHANGELOG.md > LATEST_CHANGELOG.md
        echo "✅ Restored LATEST_CHANGELOG.md from git HEAD"
        git add LATEST_CHANGELOG.md >/dev/null 2>&1 || true
    else
        # decide: interactive prompt or env var for automation
        if [ -t 0 ]; then
            read -r -p "LATEST_CHANGELOG.md not found. Create a minimal LATEST_CHANGELOG.md? [Y/n] " ans
            ans=${ans:-Y}
        else
            ans=${CREATE_LATEST_CHANGELOG:-N}
        fi

        case "$ans" in
            [Yy]*)
                cat > LATEST_CHANGELOG.md <<'EOF'
EOF
                git add LATEST_CHANGELOG.md >/dev/null 2>&1 || true
                echo "✅ Created LATEST_CHANGELOG.md"
                ;;
            *)
                echo "❌ LATEST_CHANGELOG.md missing. Please create it or set CREATE_LATEST_CHANGELOG=1 to auto-create."
                exit 1
                ;;
        esac
    fi
fi

# Create CHANGELOG.md
if [ ! -f "CHANGELOG.md" ]; then
    # try to restore from git HEAD
    if git show HEAD:./CHANGELOG.md >/dev/null 2>&1; then
        git show HEAD:./CHANGELOG.md > CHANGELOG.md
        echo "✅ Restored CHANGELOG.md from git HEAD"
        git add CHANGELOG.md >/dev/null 2>&1 || true
    else
        # decide: interactive prompt or env var for automation
        if [ -t 0 ]; then
            read -r -p "CHANGELOG.md not found. Create a minimal CHANGELOG.md? [Y/n] " ans
            ans=${ans:-Y}
        else
            ans=${CREATE_CHANGELOG:-N}
        fi

        case "$ans" in
            [Yy]*)
                cat > CHANGELOG.md <<'EOF'
EOF
                git add CHANGELOG.md >/dev/null 2>&1 || true
                echo "✅ Created CHANGELOG.md"
                ;;
            *)
                echo "❌ CHANGELOG.md missing. Please create it or set CREATE_CHANGELOG=1 to auto-create."
                exit 1
                ;;
        esac
    fi
fi

echo ""
echo "🎉 Project successfully initialized!"
echo ""
echo "📋 Next steps:"
echo "1. Make sure your GitHub repository has 'Read and write permissions' for Actions"
echo "2. Go to: Repository Settings → Actions → General → Workflow permissions"
echo "3. Select: 'Read and write permissions'"
echo "4. Check: 'Allow GitHub Actions to create and approve pull requests'"
echo ""
