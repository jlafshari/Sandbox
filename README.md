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

## How to Use

1. **Open the application**: Simply open `index.html` in your web browser.

2. **Add a task**: 
   - Type your task in the input field
   - Click the "Add" button or press Enter

3. **Complete a task**: 
   - Click the checkbox next to a task to mark it as completed
   - Completed tasks will appear with a strikethrough

4. **Delete a task**: 
   - Click the "Delete" button next to any task to remove it

5. **Clear completed tasks**: 
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
