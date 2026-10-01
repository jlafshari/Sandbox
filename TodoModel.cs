using System;
using System.Collections.Generic;
using System.Linq;

namespace TodoApp.Models
{
    /// <summary>
    /// Represents a single todo item with an ID, text description, and completion status.
    /// </summary>
    public class TodoItem
    {
        /// <summary>
        /// Gets or sets the unique identifier for the todo item.
        /// </summary>
        public long Id { get; set; }

        /// <summary>
        /// Gets or sets the text description of the todo item.
        /// </summary>
        public string Text { get; set; }

        /// <summary>
        /// Gets or sets whether the todo item has been completed.
        /// </summary>
        public bool Completed { get; set; }

        /// <summary>
        /// Creates a new todo item with the specified text.
        /// </summary>
        /// <param name="text">The text description for the todo item.</param>
        public TodoItem(string text)
        {
            Id = DateTimeOffset.UtcNow.ToUnixTimeMilliseconds();
            Text = text;
            Completed = false;
        }

        /// <summary>
        /// Parameterless constructor for serialization support.
        /// </summary>
        public TodoItem()
        {
        }
    }

    /// <summary>
    /// Manages a collection of todo items with CRUD operations.
    /// </summary>
    public class TodoManager
    {
        private List<TodoItem> _todos;

        /// <summary>
        /// Initializes a new instance of the TodoManager class.
        /// </summary>
        public TodoManager()
        {
            _todos = new List<TodoItem>();
        }

        /// <summary>
        /// Gets all todo items.
        /// </summary>
        /// <returns>A read-only list of all todo items.</returns>
        public IReadOnlyList<TodoItem> GetAll()
        {
            return _todos.AsReadOnly();
        }

        /// <summary>
        /// Gets all active (incomplete) todo items.
        /// </summary>
        /// <returns>A list of active todo items.</returns>
        public List<TodoItem> GetActive()
        {
            return _todos.Where(t => !t.Completed).ToList();
        }

        /// <summary>
        /// Gets all completed todo items.
        /// </summary>
        /// <returns>A list of completed todo items.</returns>
        public List<TodoItem> GetCompleted()
        {
            return _todos.Where(t => t.Completed).ToList();
        }

        /// <summary>
        /// Adds a new todo item with the specified text.
        /// </summary>
        /// <param name="text">The text description for the todo item.</param>
        /// <returns>The newly created todo item.</returns>
        /// <exception cref="ArgumentException">Thrown when text is null or whitespace.</exception>
        public TodoItem Add(string text)
        {
            if (string.IsNullOrWhiteSpace(text))
            {
                throw new ArgumentException("Todo text cannot be empty.", nameof(text));
            }

            var todo = new TodoItem(text);
            _todos.Add(todo);
            return todo;
        }

        /// <summary>
        /// Toggles the completion status of a todo item.
        /// </summary>
        /// <param name="id">The ID of the todo item to toggle.</param>
        /// <returns>True if the item was found and toggled; otherwise, false.</returns>
        public bool Toggle(long id)
        {
            var todo = _todos.FirstOrDefault(t => t.Id == id);
            if (todo != null)
            {
                todo.Completed = !todo.Completed;
                return true;
            }
            return false;
        }

        /// <summary>
        /// Deletes a todo item by ID.
        /// </summary>
        /// <param name="id">The ID of the todo item to delete.</param>
        /// <returns>True if the item was found and deleted; otherwise, false.</returns>
        public bool Delete(long id)
        {
            var todo = _todos.FirstOrDefault(t => t.Id == id);
            if (todo != null)
            {
                _todos.Remove(todo);
                return true;
            }
            return false;
        }

        /// <summary>
        /// Removes all completed todo items.
        /// </summary>
        /// <returns>The number of items removed.</returns>
        public int ClearCompleted()
        {
            var completedCount = _todos.Count(t => t.Completed);
            _todos = _todos.Where(t => !t.Completed).ToList();
            return completedCount;
        }

        /// <summary>
        /// Gets the count of active (incomplete) todo items.
        /// </summary>
        /// <returns>The number of active items.</returns>
        public int GetActiveCount()
        {
            return _todos.Count(t => !t.Completed);
        }

        /// <summary>
        /// Clears all todo items.
        /// </summary>
        public void Clear()
        {
            _todos.Clear();
        }
    }
}
