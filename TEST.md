# Testing the Todo List Application

## Easiest Method: Direct File Open

The **simplest way** to test this application is to:

1. Open your file browser and navigate to this project folder
2. Double-click on `index.html`
3. The application will open in your default web browser
4. Start adding, completing, and deleting tasks!

That's it! No server or special setup required.

## Alternative: Using a Local Web Server

If you prefer using a local web server (recommended for development):

### Python HTTP Server (Built-in)
```bash
# Navigate to the project directory, then run:
python3 -m http.server 8000
```

Then open your browser and go to: http://localhost:8000

### Node.js HTTP Server
```bash
npx http-server -p 8000
```

Then open your browser and go to: http://localhost:8000

## What to Test

Once the application is open in your browser, try these features:

1. **Add Tasks**
   - Type a task in the input field and click "Add" (or press Enter)
   - Try adding multiple tasks

2. **Mark as Complete**
   - Click the checkbox next to a task
   - Notice the strikethrough effect on completed tasks
   - The task counter updates automatically

3. **Delete Tasks**
   - Click the "Delete" button on any task
   - The task should be removed instantly

4. **Clear Completed**
   - Complete several tasks (check their boxes)
   - Click the "Clear Completed" button at the bottom
   - All completed tasks should be removed at once

5. **Data Persistence**
   - Add some tasks
   - Refresh the page (F5 or Ctrl+R)
   - Your tasks should still be there!
   - Close the browser tab and reopen `index.html` - tasks persist!

6. **Responsive Design**
   - Try resizing your browser window
   - The layout should adapt to different screen sizes
   - Test on mobile devices if available

## Troubleshooting

- **Tasks not persisting?** Make sure your browser allows localStorage
- **Styles not loading?** Ensure `style.css` is in the same folder as `index.html`
- **Functionality not working?** Check the browser console (F12) for errors
- **File protocol issues?** Use one of the local server methods instead

## Browser Console Testing

Open the browser console (F12) and you can also interact with tasks programmatically:

```javascript
// View all tasks
console.log(getTasks());

// Add a task programmatically
addTask();
```

Enjoy testing your new todo list application!
