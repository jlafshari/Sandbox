# Open Pull Requests in jlafshari/Sandbox

## Current Open PRs

As of the last check, there is **1 open pull request**:

### PR #1: Add todo list web application
- **Author:** jlafshari
- **Created:** 2026-09-30T20:39:40Z
- **URL:** https://github.com/jlafshari/Sandbox/pull/1

---

## How to Check for Open Pull Requests

This document explains various methods to view open pull requests in the jlafshari/Sandbox repository.

### Method 1: Using the Python Script (Recommended)
```bash
python3 check_prs.py
```

This script queries the GitHub API and displays all open pull requests with their details.

### Method 2: Using GitHub Web Interface
Visit: https://github.com/jlafshari/Sandbox/pulls

### Method 3: Using GitHub CLI (gh)
```bash
# Install gh if not already installed: https://cli.github.com/

# List all open PRs
gh pr list --state open --repo jlafshari/Sandbox

# View a specific PR
gh pr view <PR_NUMBER> --repo jlafshari/Sandbox
```

Or run the included bash script:
```bash
./check-open-prs.sh
```

### Method 4: Using GitHub API (curl)
```bash
# List open PRs
curl -s https://api.github.com/repos/jlafshari/Sandbox/pulls | jq '.'

# Without jq
curl -s https://api.github.com/repos/jlafshari/Sandbox/pulls
```

### Method 5: Using Git Commands (for local branches)
```bash
# View all branches (including remote)
git branch -a

# Fetch latest changes
git fetch --all
```

## Note
Pull requests are managed on GitHub and require either:
- Python 3 (included in this repository with check_prs.py)
- A web browser to view the GitHub interface
- GitHub CLI (gh) installed and authenticated
- API access via curl or other HTTP clients
