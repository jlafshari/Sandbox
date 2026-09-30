# Todo List Web Application

A simple, elegant todo list web application built with vanilla HTML, CSS, and JavaScript.

## Features

- ✅ Add new tasks
- ✅ Mark tasks as completed
- ✅ Delete tasks
- ✅ Clear all completed tasks
- ✅ Persistent storage using localStorage
- ✅ Responsive design
- ✅ Beautiful gradient UI

## Quick Start - Testing in Browser

There are several easy ways to test this application:

### Option 1: Direct File Open (Simplest)
1. Navigate to the project folder
2. Double-click `index.html` or right-click and select "Open with" your browser
3. The app will open directly in your browser

### Option 2: Using Python HTTP Server (Recommended)
```bash
# Python 3
python -m http.server 8000

# Or Python 2
python -m SimpleHTTPServer 8000
```
Then open http://localhost:8000 in your browser

### Option 3: Using Node.js HTTP Server
```bash
npx http-server -p 8000
```
Then open http://localhost:8000 in your browser

### Option 4: Using VS Code Live Server
If you use VS Code with the Live Server extension, right-click `index.html` and select "Open with Live Server"

## How to Use

1. **Add a task**: 
   - Type your task in the input field
   - Click the "Add" button or press Enter

2. **Complete a task**: 
   - Click the checkbox next to a task to mark it as completed
   - Completed tasks will appear with a strikethrough

3. **Delete a task**: 
   - Click the "Delete" button next to any task to remove it

4. **Clear completed tasks**: 
   - Click the "Clear Completed" button at the bottom to remove all completed tasks at once

## Technical Details

- **No dependencies**: Pure vanilla JavaScript, no frameworks required
- **Local storage**: Your tasks are saved in your browser's localStorage and persist across sessions
- **XSS protection**: User input is properly escaped to prevent security vulnerabilities
- **Responsive**: Works on desktop and mobile devices

## Files

- `index.html` - Main HTML structure
- `style.css` - Styling and layout
- `app.js` - Application logic and functionality
- `README.md` - This file

## Browser Compatibility

Works with all modern browsers that support:
- ES6 JavaScript
- localStorage API
- CSS Flexbox

Tested on Chrome, Firefox, Safari, and Edge.
