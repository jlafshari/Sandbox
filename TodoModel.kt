package com.todoapp.models

/**
 * Represents a single todo item with an ID, text description, and completion status.
 */
data class TodoItem(
    /**
     * The unique identifier for the todo item.
     */
    var id: Long = 0,
    
    /**
     * The text description of the todo item.
     */
    var text: String = "",
    
    /**
     * Whether the todo item has been completed.
     */
    var completed: Boolean = false
) {
    /**
     * Creates a new todo item with the specified text.
     * @param text The text description for the todo item.
     */
    constructor(text: String) : this(
        id = System.currentTimeMillis(),
        text = text,
        completed = false
    )
}

/**
 * Manages a collection of todo items with CRUD operations.
 */
class TodoManager {
    private val todos = mutableListOf<TodoItem>()
    
    /**
     * Gets all todo items.
     * @return A read-only list of all todo items.
     */
    fun getAll(): List<TodoItem> {
        return todos.toList()
    }
    
    /**
     * Gets all active (incomplete) todo items.
     * @return A list of active todo items.
     */
    fun getActive(): List<TodoItem> {
        return todos.filter { !it.completed }
    }
    
    /**
     * Gets all completed todo items.
     * @return A list of completed todo items.
     */
    fun getCompleted(): List<TodoItem> {
        return todos.filter { it.completed }
    }
    
    /**
     * Adds a new todo item with the specified text.
     * @param text The text description for the todo item.
     * @return The newly created todo item.
     * @throws IllegalArgumentException if text is blank.
     */
    fun add(text: String): TodoItem {
        if (text.isBlank()) {
            throw IllegalArgumentException("Todo text cannot be empty.")
        }
        
        val todo = TodoItem(text)
        todos.add(todo)
        return todo
    }
    
    /**
     * Toggles the completion status of a todo item.
     * @param id The ID of the todo item to toggle.
     * @return True if the item was found and toggled; otherwise, false.
     */
    fun toggle(id: Long): Boolean {
        val todo = todos.find { it.id == id }
        return if (todo != null) {
            todo.completed = !todo.completed
            true
        } else {
            false
        }
    }
    
    /**
     * Deletes a todo item by ID.
     * @param id The ID of the todo item to delete.
     * @return True if the item was found and deleted; otherwise, false.
     */
    fun delete(id: Long): Boolean {
        val todo = todos.find { it.id == id }
        return if (todo != null) {
            todos.remove(todo)
            true
        } else {
            false
        }
    }
    
    /**
     * Removes all completed todo items.
     * @return The number of items removed.
     */
    fun clearCompleted(): Int {
        val completedCount = todos.count { it.completed }
        todos.removeAll { it.completed }
        return completedCount
    }
    
    /**
     * Gets the count of active (incomplete) todo items.
     * @return The number of active items.
     */
    fun getActiveCount(): Int {
        return todos.count { !it.completed }
    }
    
    /**
     * Clears all todo items.
     */
    fun clear() {
        todos.clear()
    }
}
