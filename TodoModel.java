package com.todoapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a single todo item with an ID, text description, and completion status.
 */
public class TodoItem {
    /**
     * The unique identifier for the todo item.
     */
    private long id;
    
    /**
     * The text description of the todo item.
     */
    private String text;
    
    /**
     * Whether the todo item has been completed.
     */
    private boolean completed;
    
    /**
     * Creates a new todo item with the specified text.
     * @param text The text description for the todo item.
     */
    public TodoItem(String text) {
        this.id = System.currentTimeMillis();
        this.text = text;
        this.completed = false;
    }
    
    /**
     * Parameterless constructor for serialization support.
     */
    public TodoItem() {
    }
    
    /**
     * Gets the unique identifier for the todo item.
     * @return The ID of the todo item.
     */
    public long getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier for the todo item.
     * @param id The ID to set.
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Gets the text description of the todo item.
     * @return The text description.
     */
    public String getText() {
        return text;
    }
    
    /**
     * Sets the text description of the todo item.
     * @param text The text description to set.
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * Gets whether the todo item has been completed.
     * @return True if completed; otherwise, false.
     */
    public boolean isCompleted() {
        return completed;
    }
    
    /**
     * Sets whether the todo item has been completed.
     * @param completed The completion status to set.
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

/**
 * Manages a collection of todo items with CRUD operations.
 */
class TodoManager {
    private List<TodoItem> todos;
    
    /**
     * Initializes a new instance of the TodoManager class.
     */
    public TodoManager() {
        this.todos = new ArrayList<>();
    }
    
    /**
     * Gets all todo items.
     * @return A read-only list of all todo items.
     */
    public List<TodoItem> getAll() {
        return Collections.unmodifiableList(todos);
    }
    
    /**
     * Gets all active (incomplete) todo items.
     * @return A list of active todo items.
     */
    public List<TodoItem> getActive() {
        return todos.stream()
                .filter(t -> !t.isCompleted())
                .collect(Collectors.toList());
    }
    
    /**
     * Gets all completed todo items.
     * @return A list of completed todo items.
     */
    public List<TodoItem> getCompleted() {
        return todos.stream()
                .filter(TodoItem::isCompleted)
                .collect(Collectors.toList());
    }
    
    /**
     * Adds a new todo item with the specified text.
     * @param text The text description for the todo item.
     * @return The newly created todo item.
     * @throws IllegalArgumentException if text is null or blank.
     */
    public TodoItem add(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Todo text cannot be empty.");
        }
        
        TodoItem todo = new TodoItem(text);
        todos.add(todo);
        return todo;
    }
    
    /**
     * Toggles the completion status of a todo item.
     * @param id The ID of the todo item to toggle.
     * @return True if the item was found and toggled; otherwise, false.
     */
    public boolean toggle(long id) {
        for (TodoItem todo : todos) {
            if (todo.getId() == id) {
                todo.setCompleted(!todo.isCompleted());
                return true;
            }
        }
        return false;
    }
    
    /**
     * Deletes a todo item by ID.
     * @param id The ID of the todo item to delete.
     * @return True if the item was found and deleted; otherwise, false.
     */
    public boolean delete(long id) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getId() == id) {
                todos.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Removes all completed todo items.
     * @return The number of items removed.
     */
    public int clearCompleted() {
        long completedCount = todos.stream()
                .filter(TodoItem::isCompleted)
                .count();
        todos = todos.stream()
                .filter(t -> !t.isCompleted())
                .collect(Collectors.toList());
        return (int) completedCount;
    }
    
    /**
     * Gets the count of active (incomplete) todo items.
     * @return The number of active items.
     */
    public int getActiveCount() {
        return (int) todos.stream()
                .filter(t -> !t.isCompleted())
                .count();
    }
    
    /**
     * Clears all todo items.
     */
    public void clear() {
        todos.clear();
    }
}
