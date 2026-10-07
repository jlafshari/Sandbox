# Contributing to Todo List Web Application

Thank you for your interest in contributing to this project! This guide will help you get started.

## Setup

This is a simple vanilla JavaScript web application with no build process or dependencies to install.

To set up the project:

1. **Clone the repository**
   ```bash
   git clone https://github.com/jlafshari/Sandbox.git
   cd Sandbox
   ```

2. **That's it!** No npm install, no build step required. You're ready to start developing.

## Running Tests

This project uses manual testing since it's a simple client-side web application.

### Quick Start

The easiest way to test the application:

1. Open `index.html` directly in your browser by double-clicking it

### Using a Local Web Server (Recommended for Development)

For a more realistic testing environment, use a local web server:

**Python 3 (built-in):**
```bash
python3 -m http.server 8000
```
Then open http://localhost:8000

**Node.js:**
```bash
npx http-server -p 8000
```
Then open http://localhost:8000

### What to Test

See [TEST.md](TEST.md) for a comprehensive testing guide covering:
- Adding, completing, and deleting tasks
- Clearing completed tasks
- Data persistence (localStorage)
- Theme switching
- Responsive design

## Commit Message Style

This project follows the [Conventional Commits](https://www.conventionalcommits.org/) format:

```
<type>: <short description>

[optional body]
```

### Types

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Documentation changes
- **style**: Code style changes (formatting, whitespace)
- **refactor**: Code refactoring without changing behavior
- **test**: Adding or updating tests
- **chore**: Maintenance tasks, dependencies

### Examples

```
feat: add dark mode theme option

fix: prevent XSS in task input

docs: update README with GitHub Pages instructions

refactor: simplify toggleTodo function
```

### Guidelines

- Use the imperative mood ("add feature" not "added feature")
- Keep the subject line under 50 characters
- Capitalize the subject line
- Don't end the subject line with a period
- Separate subject from body with a blank line
- Use the body to explain *what* and *why*, not *how*

## Code Style

- **Indentation**: 2 spaces (no tabs)
- **Line endings**: LF (Unix-style)
- **Quotes**: Single quotes for JavaScript strings
- **Semicolons**: Always use semicolons in JavaScript
- **Naming**: camelCase for functions and variables

Follow the existing code style in the files you're editing. The project includes an `.editorconfig` file to help maintain consistency.

## Questions?

If you have questions about contributing, please open an issue and we'll be happy to help!
