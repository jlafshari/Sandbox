#!/bin/bash
# Script to check open pull requests in this repository
# Requires GitHub CLI (gh) to be installed and authenticated

echo "Checking for open pull requests..."
echo ""

# Check if gh is installed
if ! command -v gh &> /dev/null; then
    echo "Error: GitHub CLI (gh) is not installed."
    echo "Please install it from: https://cli.github.com/"
    echo ""
    echo "Alternative methods:"
    echo "1. Visit: https://github.com/jlafshari/Sandbox/pulls"
    echo "2. Use the GitHub API: curl -s https://api.github.com/repos/jlafshari/Sandbox/pulls"
    exit 1
fi

# List open pull requests
gh pr list --state open --repo jlafshari/Sandbox

echo ""
echo "To view details of a specific PR, run: gh pr view <PR_NUMBER>"
