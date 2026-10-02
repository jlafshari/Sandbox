"""
test_todomodel.py - Unit tests for the TodoModel module.

This module provides comprehensive tests for TodoItem and TodoManager classes.
"""

import unittest
import time
from TodoModel import TodoItem, TodoManager


class TestTodoItem(unittest.TestCase):
    """Test cases for TodoItem class."""

    def test_init_with_text(self):
        """Test TodoItem initialization with text."""
        item = TodoItem("Buy groceries")
        self.assertEqual(item.text, "Buy groceries")
        self.assertFalse(item.completed)
        self.assertIsInstance(item.id, int)
        self.assertGreater(item.id, 0)

    def test_init_with_text_and_id(self):
        """Test TodoItem initialization with text and custom ID."""
        item = TodoItem("Buy groceries", 12345)
        self.assertEqual(item.text, "Buy groceries")
        self.assertEqual(item.id, 12345)
        self.assertFalse(item.completed)

    def test_init_default_text(self):
        """Test TodoItem initialization with default empty text."""
        item = TodoItem()
        self.assertEqual(item.text, "")
        self.assertFalse(item.completed)

    def test_repr(self):
        """Test TodoItem string representation."""
        item = TodoItem("Test task", 999)
        repr_str = repr(item)
        self.assertIn("TodoItem", repr_str)
        self.assertIn("id=999", repr_str)
        self.assertIn("text='Test task'", repr_str)
        self.assertIn("completed=False", repr_str)

    def test_equality_same_items(self):
        """Test equality of identical TodoItems."""
        item1 = TodoItem("Task", 100)
        item2 = TodoItem("Task", 100)
        self.assertEqual(item1, item2)

    def test_equality_different_ids(self):
        """Test inequality of TodoItems with different IDs."""
        item1 = TodoItem("Task", 100)
        item2 = TodoItem("Task", 200)
        self.assertNotEqual(item1, item2)

    def test_equality_different_text(self):
        """Test inequality of TodoItems with different text."""
        item1 = TodoItem("Task A", 100)
        item2 = TodoItem("Task B", 100)
        self.assertNotEqual(item1, item2)

    def test_equality_different_completed_status(self):
        """Test inequality of TodoItems with different completion status."""
        item1 = TodoItem("Task", 100)
        item2 = TodoItem("Task", 100)
        item2.completed = True
        self.assertNotEqual(item1, item2)

    def test_equality_with_non_todoitem(self):
        """Test inequality when comparing with non-TodoItem."""
        item = TodoItem("Task", 100)
        self.assertNotEqual(item, "Not a TodoItem")
        self.assertNotEqual(item, 100)
        self.assertNotEqual(item, None)


class TestTodoManager(unittest.TestCase):
    """Test cases for TodoManager class."""

    def setUp(self):
        """Set up a fresh TodoManager for each test."""
        self.manager = TodoManager()

    def test_init(self):
        """Test TodoManager initialization."""
        self.assertIsInstance(self.manager.items, list)
        self.assertEqual(len(self.manager.items), 0)

    def test_add_valid_item(self):
        """Test adding a valid todo item."""
        item = self.manager.add("Buy milk")
        self.assertEqual(item.text, "Buy milk")
        self.assertEqual(len(self.manager.items), 1)
        self.assertIn(item, self.manager.items)

    def test_add_empty_text_raises_error(self):
        """Test that adding empty text raises ValueError."""
        with self.assertRaises(ValueError) as context:
            self.manager.add("")
        self.assertIn("empty or blank", str(context.exception))

    def test_add_blank_text_raises_error(self):
        """Test that adding blank text raises ValueError."""
        with self.assertRaises(ValueError) as context:
            self.manager.add("   ")
        self.assertIn("empty or blank", str(context.exception))

    def test_add_multiple_items(self):
        """Test adding multiple todo items."""
        item1 = self.manager.add("Task 1")
        item2 = self.manager.add("Task 2")
        item3 = self.manager.add("Task 3")
        
        self.assertEqual(len(self.manager.items), 3)
        self.assertEqual(item1.text, "Task 1")
        self.assertEqual(item2.text, "Task 2")
        self.assertEqual(item3.text, "Task 3")

    def test_get_all(self):
        """Test getting all todo items."""
        self.manager.add("Task 1")
        self.manager.add("Task 2")
        
        all_items = self.manager.get_all()
        self.assertEqual(len(all_items), 2)
        # Verify it returns a copy, not the original list
        all_items.append(TodoItem("Extra"))
        self.assertEqual(len(self.manager.items), 2)

    def test_get_all_empty(self):
        """Test getting all items from empty manager."""
        all_items = self.manager.get_all()
        self.assertEqual(len(all_items), 0)

    def test_get_active(self):
        """Test getting active (incomplete) todo items."""
        item1 = self.manager.add("Active task 1")
        item2 = self.manager.add("Completed task")
        item3 = self.manager.add("Active task 2")
        
        item2.completed = True
        
        active_items = self.manager.get_active()
        self.assertEqual(len(active_items), 2)
        self.assertIn(item1, active_items)
        self.assertIn(item3, active_items)
        self.assertNotIn(item2, active_items)

    def test_get_active_empty(self):
        """Test getting active items when all are completed."""
        item = self.manager.add("Task")
        item.completed = True
        
        active_items = self.manager.get_active()
        self.assertEqual(len(active_items), 0)

    def test_get_completed(self):
        """Test getting completed todo items."""
        item1 = self.manager.add("Active task")
        item2 = self.manager.add("Completed task 1")
        item3 = self.manager.add("Completed task 2")
        
        item2.completed = True
        item3.completed = True
        
        completed_items = self.manager.get_completed()
        self.assertEqual(len(completed_items), 2)
        self.assertIn(item2, completed_items)
        self.assertIn(item3, completed_items)
        self.assertNotIn(item1, completed_items)

    def test_get_completed_empty(self):
        """Test getting completed items when none are completed."""
        self.manager.add("Task 1")
        self.manager.add("Task 2")
        
        completed_items = self.manager.get_completed()
        self.assertEqual(len(completed_items), 0)

    def test_toggle_existing_item(self):
        """Test toggling an existing todo item."""
        item = self.manager.add("Task")
        self.assertFalse(item.completed)
        
        result = self.manager.toggle(item.id)
        self.assertTrue(result)
        self.assertTrue(item.completed)
        
        result = self.manager.toggle(item.id)
        self.assertTrue(result)
        self.assertFalse(item.completed)

    def test_toggle_nonexistent_item(self):
        """Test toggling a nonexistent todo item."""
        result = self.manager.toggle(99999)
        self.assertFalse(result)

    def test_delete_existing_item(self):
        """Test deleting an existing todo item."""
        item1 = self.manager.add("Task 1")
        time.sleep(0.001)  # Small delay to ensure unique IDs
        item2 = self.manager.add("Task 2")
        time.sleep(0.001)
        item3 = self.manager.add("Task 3")
        
        # Store the original item count and IDs
        original_count = len(self.manager.items)
        item2_id = item2.id
        
        result = self.manager.delete(item2_id)
        self.assertTrue(result)
        self.assertEqual(len(self.manager.items), 2)
        
        # Verify item2 is no longer in the list
        remaining_ids = [item.id for item in self.manager.items]
        self.assertNotIn(item2_id, remaining_ids)

    def test_delete_nonexistent_item(self):
        """Test deleting a nonexistent todo item."""
        self.manager.add("Task")
        result = self.manager.delete(99999)
        self.assertFalse(result)
        self.assertEqual(len(self.manager.items), 1)

    def test_clear_completed_some_items(self):
        """Test clearing completed items when some exist."""
        item1 = self.manager.add("Task 1")
        item2 = self.manager.add("Task 2")
        item3 = self.manager.add("Task 3")
        item4 = self.manager.add("Task 4")
        
        item2.completed = True
        item4.completed = True
        
        count = self.manager.clear_completed()
        self.assertEqual(count, 2)
        self.assertEqual(len(self.manager.items), 2)
        self.assertIn(item1, self.manager.items)
        self.assertIn(item3, self.manager.items)
        self.assertNotIn(item2, self.manager.items)
        self.assertNotIn(item4, self.manager.items)

    def test_clear_completed_none_completed(self):
        """Test clearing completed items when none are completed."""
        self.manager.add("Task 1")
        self.manager.add("Task 2")
        
        count = self.manager.clear_completed()
        self.assertEqual(count, 0)
        self.assertEqual(len(self.manager.items), 2)

    def test_clear_completed_all_completed(self):
        """Test clearing completed items when all are completed."""
        item1 = self.manager.add("Task 1")
        item2 = self.manager.add("Task 2")
        item1.completed = True
        item2.completed = True
        
        count = self.manager.clear_completed()
        self.assertEqual(count, 2)
        self.assertEqual(len(self.manager.items), 0)

    def test_get_active_count(self):
        """Test getting the count of active todo items."""
        self.assertEqual(self.manager.get_active_count(), 0)
        
        item1 = self.manager.add("Task 1")
        item2 = self.manager.add("Task 2")
        item3 = self.manager.add("Task 3")
        
        self.assertEqual(self.manager.get_active_count(), 3)
        
        item2.completed = True
        self.assertEqual(self.manager.get_active_count(), 2)
        
        item1.completed = True
        item3.completed = True
        self.assertEqual(self.manager.get_active_count(), 0)

    def test_clear(self):
        """Test clearing all todo items."""
        self.manager.add("Task 1")
        self.manager.add("Task 2")
        self.manager.add("Task 3")
        
        self.assertEqual(len(self.manager.items), 3)
        
        self.manager.clear()
        self.assertEqual(len(self.manager.items), 0)

    def test_clear_empty_manager(self):
        """Test clearing an already empty manager."""
        self.manager.clear()
        self.assertEqual(len(self.manager.items), 0)


if __name__ == "__main__":
    unittest.main()
