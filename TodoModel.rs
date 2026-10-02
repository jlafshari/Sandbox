/// TodoModel.rs - Rust implementation of the Todo data model.
///
/// This module provides TodoItem and TodoManager structures that mirror the
/// frontend JavaScript application and other backend implementations.

use std::time::{SystemTime, UNIX_EPOCH};

/// Represents a single todo item with an ID, text description, and completion status.
#[derive(Debug, Clone, PartialEq)]
pub struct TodoItem {
    /// The unique identifier for the todo item.
    pub id: u64,
    
    /// The text description of the todo item.
    pub text: String,
    
    /// Whether the todo item has been completed.
    pub completed: bool,
}

impl TodoItem {
    /// Creates a new todo item with the specified text.
    ///
    /// # Arguments
    ///
    /// * `text` - The text description for the todo item.
    ///
    /// # Examples
    ///
    /// ```
    /// let todo = TodoItem::new("Buy groceries".to_string());
    /// assert_eq!(todo.text, "Buy groceries");
    /// assert_eq!(todo.completed, false);
    /// ```
    pub fn new(text: String) -> Self {
        let id = SystemTime::now()
            .duration_since(UNIX_EPOCH)
            .expect("Time went backwards")
            .as_millis() as u64;
        
        TodoItem {
            id,
            text,
            completed: false,
        }
    }
    
    /// Creates a new todo item with specified id, text, and completion status.
    /// Used for deserialization or testing.
    ///
    /// # Arguments
    ///
    /// * `id` - The unique identifier for the todo item.
    /// * `text` - The text description for the todo item.
    /// * `completed` - Whether the todo item has been completed.
    pub fn with_id(id: u64, text: String, completed: bool) -> Self {
        TodoItem {
            id,
            text,
            completed,
        }
    }
}

/// Manages a collection of todo items with CRUD operations.
#[derive(Debug)]
pub struct TodoManager {
    todos: Vec<TodoItem>,
}

impl TodoManager {
    /// Creates a new empty TodoManager.
    ///
    /// # Examples
    ///
    /// ```
    /// let manager = TodoManager::new();
    /// assert_eq!(manager.get_all().len(), 0);
    /// ```
    pub fn new() -> Self {
        TodoManager { todos: Vec::new() }
    }
    
    /// Gets all todo items.
    ///
    /// # Returns
    ///
    /// A vector of references to all todo items.
    pub fn get_all(&self) -> Vec<&TodoItem> {
        self.todos.iter().collect()
    }
    
    /// Gets all active (incomplete) todo items.
    ///
    /// # Returns
    ///
    /// A vector of references to active todo items.
    pub fn get_active(&self) -> Vec<&TodoItem> {
        self.todos.iter().filter(|t| !t.completed).collect()
    }
    
    /// Gets all completed todo items.
    ///
    /// # Returns
    ///
    /// A vector of references to completed todo items.
    pub fn get_completed(&self) -> Vec<&TodoItem> {
        self.todos.iter().filter(|t| t.completed).collect()
    }
    
    /// Adds a new todo item with the specified text.
    ///
    /// # Arguments
    ///
    /// * `text` - The text description for the todo item.
    ///
    /// # Returns
    ///
    /// A reference to the newly created todo item.
    ///
    /// # Errors
    ///
    /// Returns an error string if text is empty or blank.
    ///
    /// # Examples
    ///
    /// ```
    /// let mut manager = TodoManager::new();
    /// let result = manager.add("Buy groceries".to_string());
    /// assert!(result.is_ok());
    /// ```
    pub fn add(&mut self, text: String) -> Result<&TodoItem, String> {
        if text.trim().is_empty() {
            return Err("Todo text cannot be empty.".to_string());
        }
        
        let todo = TodoItem::new(text);
        self.todos.push(todo);
        Ok(self.todos.last().unwrap())
    }
    
    /// Toggles the completion status of a todo item.
    ///
    /// # Arguments
    ///
    /// * `id` - The ID of the todo item to toggle.
    ///
    /// # Returns
    ///
    /// `true` if the item was found and toggled; otherwise, `false`.
    pub fn toggle(&mut self, id: u64) -> bool {
        if let Some(todo) = self.todos.iter_mut().find(|t| t.id == id) {
            todo.completed = !todo.completed;
            true
        } else {
            false
        }
    }
    
    /// Deletes a todo item by ID.
    ///
    /// # Arguments
    ///
    /// * `id` - The ID of the todo item to delete.
    ///
    /// # Returns
    ///
    /// `true` if the item was found and deleted; otherwise, `false`.
    pub fn delete(&mut self, id: u64) -> bool {
        if let Some(pos) = self.todos.iter().position(|t| t.id == id) {
            self.todos.remove(pos);
            true
        } else {
            false
        }
    }
    
    /// Removes all completed todo items.
    ///
    /// # Returns
    ///
    /// The number of items removed.
    pub fn clear_completed(&mut self) -> usize {
        let initial_count = self.todos.len();
        self.todos.retain(|t| !t.completed);
        initial_count - self.todos.len()
    }
    
    /// Gets the count of active (incomplete) todo items.
    ///
    /// # Returns
    ///
    /// The number of active items.
    pub fn get_active_count(&self) -> usize {
        self.todos.iter().filter(|t| !t.completed).count()
    }
    
    /// Clears all todo items.
    pub fn clear(&mut self) {
        self.todos.clear();
    }
}

impl Default for TodoManager {
    fn default() -> Self {
        Self::new()
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_todo_item_new() {
        let todo = TodoItem::new("Test task".to_string());
        assert_eq!(todo.text, "Test task");
        assert_eq!(todo.completed, false);
        assert!(todo.id > 0);
    }

    #[test]
    fn test_todo_manager_add() {
        let mut manager = TodoManager::new();
        let result = manager.add("Buy milk".to_string());
        assert!(result.is_ok());
        assert_eq!(manager.get_all().len(), 1);
    }

    #[test]
    fn test_todo_manager_add_empty() {
        let mut manager = TodoManager::new();
        let result = manager.add("".to_string());
        assert!(result.is_err());
        assert_eq!(manager.get_all().len(), 0);
    }

    #[test]
    fn test_todo_manager_toggle() {
        let mut manager = TodoManager::new();
        manager.add("Test".to_string()).unwrap();
        let id = manager.get_all()[0].id;
        
        assert_eq!(manager.get_all()[0].completed, false);
        assert!(manager.toggle(id));
        assert_eq!(manager.get_all()[0].completed, true);
        assert!(manager.toggle(id));
        assert_eq!(manager.get_all()[0].completed, false);
    }

    #[test]
    fn test_todo_manager_delete() {
        let mut manager = TodoManager::new();
        manager.add("Test".to_string()).unwrap();
        let id = manager.get_all()[0].id;
        
        assert_eq!(manager.get_all().len(), 1);
        assert!(manager.delete(id));
        assert_eq!(manager.get_all().len(), 0);
        assert!(!manager.delete(id)); // Already deleted
    }

    #[test]
    fn test_todo_manager_get_active_and_completed() {
        let mut manager = TodoManager::new();
        manager.add("Task 1".to_string()).unwrap();
        manager.add("Task 2".to_string()).unwrap();
        manager.add("Task 3".to_string()).unwrap();
        
        let id2 = manager.get_all()[1].id;
        manager.toggle(id2);
        
        assert_eq!(manager.get_active().len(), 2);
        assert_eq!(manager.get_completed().len(), 1);
        assert_eq!(manager.get_active_count(), 2);
    }

    #[test]
    fn test_todo_manager_clear_completed() {
        let mut manager = TodoManager::new();
        manager.add("Task 1".to_string()).unwrap();
        manager.add("Task 2".to_string()).unwrap();
        manager.add("Task 3".to_string()).unwrap();
        
        let id1 = manager.get_all()[0].id;
        let id2 = manager.get_all()[1].id;
        manager.toggle(id1);
        manager.toggle(id2);
        
        let removed = manager.clear_completed();
        assert_eq!(removed, 2);
        assert_eq!(manager.get_all().len(), 1);
    }

    #[test]
    fn test_todo_manager_clear() {
        let mut manager = TodoManager::new();
        manager.add("Task 1".to_string()).unwrap();
        manager.add("Task 2".to_string()).unwrap();
        
        assert_eq!(manager.get_all().len(), 2);
        manager.clear();
        assert_eq!(manager.get_all().len(), 0);
    }
}
