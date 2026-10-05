package com.todoapp.models

import java.time.Instant
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

/**
 * Represents a single todo item with an ID, text description, and completion status.
 * 
 * This is an immutable data class that follows Kotlin best practices. All properties
 * are read-only (val) to ensure thread safety and predictability.
 *
 * # Examples
 *
 * ```kotlin
 * // Create a new todo item with auto-generated timestamp ID
 * val todo = TodoItem.create("Buy groceries")
 * println(todo.text) // "Buy groceries"
 * println(todo.completed) // false
 * 
 * // Create a todo item with specific values (for deserialization or testing)
 * val specificTodo = TodoItem(id = 12345L, text = "Complete project", completed = true)
 * ```
 */
data class TodoItem(
    /**
     * The unique identifier for the todo item.
     * Typically a timestamp in milliseconds since epoch.
     */
    val id: Long,
    
    /**
     * The text description of the todo item.
     */
    val text: String,
    
    /**
     * Whether the todo item has been completed.
     */
    val completed: Boolean = false
) {
    companion object {
        /**
         * Creates a new todo item with the specified text and auto-generated ID.
         * 
         * @param text The text description for the todo item.
         * @return A new TodoItem with a timestamp-based ID.
         *
         * # Examples
         *
         * ```kotlin
         * val todo = TodoItem.create("Buy milk")
         * assert(todo.id > 0)
         * assert(todo.text == "Buy milk")
         * assert(!todo.completed)
         * ```
         */
        fun create(text: String): TodoItem {
            return TodoItem(
                id = Instant.now().toEpochMilli(),
                text = text,
                completed = false
            )
        }
    }
}

/**
 * Manages a collection of todo items with CRUD operations.
 * 
 * This class provides a simple in-memory todo list implementation with
 * methods for adding, toggling, deleting, and querying todo items.
 *
 * # Examples
 *
 * ```kotlin
 * val manager = TodoManager()
 * 
 * // Add some todos
 * val todo1 = manager.add("Buy groceries")
 * val todo2 = manager.add("Write report")
 * 
 * // Toggle completion status
 * manager.toggle(todo1.id)
 * 
 * // Query todos
 * println("Active: ${manager.getActiveCount()}")
 * println("All: ${manager.getAll().size}")
 * 
 * // Clear completed
 * manager.clearCompleted()
 * ```
 */
class TodoManager {
    private val todos = mutableListOf<TodoItem>()
    
    /**
     * Gets all todo items.
     * 
     * @return A read-only list of all todo items.
     */
    fun getAll(): List<TodoItem> {
        return todos.toList()
    }
    
    /**
     * Gets all active (incomplete) todo items.
     * 
     * @return A list of active todo items.
     */
    fun getActive(): List<TodoItem> {
        return todos.filter { !it.completed }
    }
    
    /**
     * Gets all completed todo items.
     * 
     * @return A list of completed todo items.
     */
    fun getCompleted(): List<TodoItem> {
        return todos.filter { it.completed }
    }
    
    /**
     * Adds a new todo item with the specified text.
     * 
     * @param text The text description for the todo item.
     * @return The newly created todo item.
     * @throws IllegalArgumentException if text is null, blank, or empty.
     *
     * # Examples
     *
     * ```kotlin
     * val manager = TodoManager()
     * val todo = manager.add("Buy milk")
     * 
     * // This will throw an exception
     * try {
     *     manager.add("")
     * } catch (e: IllegalArgumentException) {
     *     println("Cannot add empty todo")
     * }
     * ```
     */
    fun add(text: String?): TodoItem {
        // Check for null explicitly even though Kotlin's type system helps
        if (text == null || text.trim().isEmpty()) {
            throw IllegalArgumentException("Todo text cannot be empty.")
        }
        
        val todo = TodoItem.create(text)
        todos.add(todo)
        return todo
    }
    
    /**
     * Toggles the completion status of a todo item.
     * 
     * Since TodoItem is immutable, this creates a new TodoItem with the
     * toggled completion status and replaces the old one in the list.
     * 
     * @param id The ID of the todo item to toggle.
     * @return True if the item was found and toggled; otherwise, false.
     *
     * # Examples
     *
     * ```kotlin
     * val manager = TodoManager()
     * val todo = manager.add("Buy milk")
     * 
     * val toggled = manager.toggle(todo.id)
     * assert(toggled)
     * assert(manager.getAll()[0].completed)
     * ```
     */
    fun toggle(id: Long): Boolean {
        val index = todos.indexOfFirst { it.id == id }
        return if (index != -1) {
            val todo = todos[index]
            todos[index] = todo.copy(completed = !todo.completed)
            true
        } else {
            false
        }
    }
    
    /**
     * Deletes a todo item by ID.
     * 
     * @param id The ID of the todo item to delete.
     * @return True if the item was found and deleted; otherwise, false.
     *
     * # Examples
     *
     * ```kotlin
     * val manager = TodoManager()
     * val todo = manager.add("Buy milk")
     * 
     * val deleted = manager.delete(todo.id)
     * assert(deleted)
     * assert(manager.getAll().isEmpty())
     * 
     * // Second delete returns false
     * val deletedAgain = manager.delete(todo.id)
     * assert(!deletedAgain)
     * ```
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
     * 
     * @return The number of items removed.
     *
     * # Examples
     *
     * ```kotlin
     * val manager = TodoManager()
     * manager.add("Task 1")
     * val todo2 = manager.add("Task 2")
     * manager.toggle(todo2.id)
     * 
     * val removed = manager.clearCompleted()
     * assert(removed == 1)
     * assert(manager.getAll().size == 1)
     * ```
     */
    fun clearCompleted(): Int {
        val completedCount = todos.count { it.completed }
        todos.removeAll { it.completed }
        return completedCount
    }
    
    /**
     * Gets the count of active (incomplete) todo items.
     * 
     * @return The number of active items.
     */
    fun getActiveCount(): Int {
        return todos.count { !it.completed }
    }
    
    /**
     * Clears all todo items.
     *
     * # Examples
     *
     * ```kotlin
     * val manager = TodoManager()
     * manager.add("Task 1")
     * manager.add("Task 2")
     * 
     * manager.clear()
     * assert(manager.getAll().isEmpty())
     * ```
     */
    fun clear() {
        todos.clear()
    }
}

// ============================================================================
// Unit Tests
// ============================================================================

/**
 * Unit tests for TodoItem and TodoManager.
 * 
 * These tests verify all CRUD operations, edge cases, and error handling
 * to ensure the todo model behaves correctly.
 */
class TodoModelTest {
    
    @Test
    fun `test TodoItem create with factory method`() {
        val todo = TodoItem.create("Test task")
        assertEquals("Test task", todo.text)
        assertFalse(todo.completed)
        assertTrue(todo.id > 0, "ID should be a positive timestamp")
    }
    
    @Test
    fun `test TodoItem data class immutability`() {
        val todo = TodoItem.create("Original")
        val modified = todo.copy(text = "Modified", completed = true)
        
        // Original should be unchanged
        assertEquals("Original", todo.text)
        assertFalse(todo.completed)
        
        // Modified should have new values
        assertEquals("Modified", modified.text)
        assertTrue(modified.completed)
        assertEquals(todo.id, modified.id) // ID should be the same
    }
    
    @Test
    fun `test TodoManager add todo`() {
        val manager = TodoManager()
        val todo = manager.add("Buy milk")
        
        assertEquals(1, manager.getAll().size)
        assertEquals("Buy milk", todo.text)
        assertFalse(todo.completed)
    }
    
    @Test
    fun `test TodoManager add empty text throws exception`() {
        val manager = TodoManager()
        
        assertThrows<IllegalArgumentException> {
            manager.add("")
        }
        
        assertEquals(0, manager.getAll().size)
    }
    
    @Test
    fun `test TodoManager add blank text throws exception`() {
        val manager = TodoManager()
        
        assertThrows<IllegalArgumentException> {
            manager.add("   ")
        }
        
        assertEquals(0, manager.getAll().size)
    }
    
    @Test
    fun `test TodoManager add null text throws exception`() {
        val manager = TodoManager()
        
        assertThrows<IllegalArgumentException> {
            manager.add(null)
        }
        
        assertEquals(0, manager.getAll().size)
    }
    
    @Test
    fun `test TodoManager toggle completion status`() {
        val manager = TodoManager()
        val todo = manager.add("Test")
        val id = todo.id
        
        assertFalse(manager.getAll()[0].completed)
        
        assertTrue(manager.toggle(id))
        assertTrue(manager.getAll()[0].completed)
        
        assertTrue(manager.toggle(id))
        assertFalse(manager.getAll()[0].completed)
    }
    
    @Test
    fun `test TodoManager toggle non-existent item returns false`() {
        val manager = TodoManager()
        manager.add("Test")
        
        assertFalse(manager.toggle(99999L))
    }
    
    @Test
    fun `test TodoManager delete todo`() {
        val manager = TodoManager()
        val todo = manager.add("Test")
        val id = todo.id
        
        assertEquals(1, manager.getAll().size)
        assertTrue(manager.delete(id))
        assertEquals(0, manager.getAll().size)
        
        // Second delete should return false
        assertFalse(manager.delete(id))
    }
    
    @Test
    fun `test TodoManager delete non-existent item returns false`() {
        val manager = TodoManager()
        manager.add("Test")
        
        assertFalse(manager.delete(99999L))
    }
    
    @Test
    fun `test TodoManager get active and completed`() {
        val manager = TodoManager()
        val todo1 = manager.add("Task 1")
        val todo2 = manager.add("Task 2")
        val todo3 = manager.add("Task 3")
        
        manager.toggle(todo2.id)
        
        assertEquals(2, manager.getActive().size)
        assertEquals(1, manager.getCompleted().size)
        assertEquals(2, manager.getActiveCount())
        
        assertTrue(manager.getActive().any { it.id == todo1.id })
        assertTrue(manager.getActive().any { it.id == todo3.id })
        assertTrue(manager.getCompleted().any { it.id == todo2.id })
    }
    
    @Test
    fun `test TodoManager clear completed`() {
        val manager = TodoManager()
        val todo1 = manager.add("Task 1")
        val todo2 = manager.add("Task 2")
        val todo3 = manager.add("Task 3")
        
        manager.toggle(todo1.id)
        manager.toggle(todo2.id)
        
        val removed = manager.clearCompleted()
        assertEquals(2, removed)
        assertEquals(1, manager.getAll().size)
        assertEquals(todo3.id, manager.getAll()[0].id)
    }
    
    @Test
    fun `test TodoManager clear completed with no completed items`() {
        val manager = TodoManager()
        manager.add("Task 1")
        manager.add("Task 2")
        
        val removed = manager.clearCompleted()
        assertEquals(0, removed)
        assertEquals(2, manager.getAll().size)
    }
    
    @Test
    fun `test TodoManager clear all`() {
        val manager = TodoManager()
        manager.add("Task 1")
        manager.add("Task 2")
        
        assertEquals(2, manager.getAll().size)
        manager.clear()
        assertEquals(0, manager.getAll().size)
    }
    
    @Test
    fun `test TodoManager operations on empty list`() {
        val manager = TodoManager()
        
        assertEquals(0, manager.getAll().size)
        assertEquals(0, manager.getActive().size)
        assertEquals(0, manager.getCompleted().size)
        assertEquals(0, manager.getActiveCount())
        assertEquals(0, manager.clearCompleted())
        
        assertFalse(manager.toggle(1L))
        assertFalse(manager.delete(1L))
    }
    
    @Test
    fun `test TodoItem equality and hashCode`() {
        val todo1 = TodoItem(id = 1L, text = "Test", completed = false)
        val todo2 = TodoItem(id = 1L, text = "Test", completed = false)
        val todo3 = TodoItem(id = 2L, text = "Test", completed = false)
        
        // Data classes auto-generate equals and hashCode
        assertEquals(todo1, todo2)
        assertEquals(todo1.hashCode(), todo2.hashCode())
        assertNotEquals(todo1, todo3)
    }
    
    @Test
    fun `test TodoItem toString`() {
        val todo = TodoItem(id = 123L, text = "Test task", completed = true)
        val str = todo.toString()
        
        // Data classes auto-generate toString
        assertTrue(str.contains("123"))
        assertTrue(str.contains("Test task"))
        assertTrue(str.contains("true"))
    }
}
