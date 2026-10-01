"""
TodoModel.py - Python implementation of the Todo data model.

This module provides TodoItem and TodoManager classes that mirror the
frontend JavaScript application and other backend implementations.
"""

from datetime import datetime
from typing import List, Optional


class TodoItem:
    """Represents a single todo item with id, text, and completion status."""

    def __init__(self, text: str = "", todo_id: Optional[int] = None):
        """
        Initialize a TodoItem.

        Args:
            text: Description of the todo item (default: empty string)
            todo_id: Unique identifier (default: current timestamp in milliseconds)
        """
        self.id = todo_id if todo_id is not None else int(datetime.now().timestamp() * 1000)
        self.text = text
        self.completed = False

    def __repr__(self) -> str:
        """Return string representation of TodoItem."""
        return f"TodoItem(id={self.id}, text='{self.text}', completed={self.completed})"

    def __eq__(self, other) -> bool:
        """Check equality based on id, text, and completed status."""
        if not isinstance(other, TodoItem):
            return False
        return self.id == other.id and self.text == other.text and self.completed == other.completed


class TodoManager:
    """Manages a collection of TodoItem objects with CRUD operations."""

    def __init__(self):
        """Initialize an empty TodoManager."""
        self.items: List[TodoItem] = []

    def get_all(self) -> List[TodoItem]:
        """
        Get all todo items.

        Returns:
            List of all TodoItem objects
        """
        return self.items.copy()

    def get_active(self) -> List[TodoItem]:
        """
        Get all incomplete todo items.

        Returns:
            List of TodoItem objects with completed=False
        """
        return [item for item in self.items if not item.completed]

    def get_completed(self) -> List[TodoItem]:
        """
        Get all completed todo items.

        Returns:
            List of TodoItem objects with completed=True
        """
        return [item for item in self.items if item.completed]

    def add(self, text: str) -> TodoItem:
        """
        Add a new todo item.

        Args:
            text: Description of the todo item

        Returns:
            The newly created TodoItem

        Raises:
            ValueError: If text is empty or blank
        """
        if not text or not text.strip():
            raise ValueError("Todo text cannot be empty or blank")
        
        new_item = TodoItem(text)
        self.items.append(new_item)
        return new_item

    def toggle(self, todo_id: int) -> bool:
        """
        Toggle the completion status of a todo item.

        Args:
            todo_id: ID of the todo item to toggle

        Returns:
            True if item was found and toggled, False otherwise
        """
        for item in self.items:
            if item.id == todo_id:
                item.completed = not item.completed
                return True
        return False

    def delete(self, todo_id: int) -> bool:
        """
        Delete a todo item by ID.

        Args:
            todo_id: ID of the todo item to delete

        Returns:
            True if item was found and deleted, False otherwise
        """
        for i, item in enumerate(self.items):
            if item.id == todo_id:
                self.items.pop(i)
                return True
        return False

    def clear_completed(self) -> int:
        """
        Remove all completed todo items.

        Returns:
            Number of items removed
        """
        initial_count = len(self.items)
        self.items = [item for item in self.items if not item.completed]
        return initial_count - len(self.items)

    def get_active_count(self) -> int:
        """
        Get the count of incomplete todo items.

        Returns:
            Number of items with completed=False
        """
        return len(self.get_active())

    def clear(self) -> None:
        """Remove all todo items."""
        self.items.clear()
