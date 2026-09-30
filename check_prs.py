#!/usr/bin/env python3
"""
Script to check open pull requests in the jlafshari/Sandbox repository
Uses Python's urllib to query GitHub API
"""

import json
import urllib.request
import sys

def get_open_prs():
    url = "https://api.github.com/repos/jlafshari/Sandbox/pulls"
    
    try:
        with urllib.request.urlopen(url, timeout=10) as response:
            data = json.loads(response.read().decode())
            
            if not data:
                print("No open pull requests found.")
                return
            
            print(f"Found {len(data)} open pull request(s):\n")
            
            for pr in data:
                print(f"PR #{pr['number']}: {pr['title']}")
                print(f"  Author: {pr['user']['login']}")
                print(f"  Created: {pr['created_at']}")
                print(f"  URL: {pr['html_url']}")
                print()
                
    except urllib.error.HTTPError as e:
        print(f"HTTP Error: {e.code} - {e.reason}")
        sys.exit(1)
    except urllib.error.URLError as e:
        print(f"URL Error: {e.reason}")
        sys.exit(1)
    except Exception as e:
        print(f"Error: {str(e)}")
        sys.exit(1)

if __name__ == "__main__":
    print("Checking for open pull requests in jlafshari/Sandbox...\n")
    get_open_prs()
