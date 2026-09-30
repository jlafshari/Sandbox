// Todo List App with localStorage persistence

class TodoList {
    constructor() {
        this.todos = this.loadFromStorage();
        this.todoInput = document.getElementById('todoInput');
        this.addBtn = document.getElementById('addBtn');
        this.todoListEl = document.getElementById('todoList');
        this.taskCount = document.getElementById('taskCount');
        this.clearCompletedBtn = document.getElementById('clearCompleted');
        
        this.initEventListeners();
        this.render();
    }
    
    initEventListeners() {
        // Add todo on button click
        this.addBtn.addEventListener('click', () => this.addTodo());
        
        // Add todo on Enter key press
        this.todoInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.addTodo();
            }
        });
        
        // Clear completed todos
        this.clearCompletedBtn.addEventListener('click', () => this.clearCompleted());
    }
    
    addTodo() {
        const text = this.todoInput.value.trim();
        
        if (text === '') {
            return;
        }
        
        const todo = {
            id: Date.now(),
            text: text,
            completed: false
        };
        
        this.todos.push(todo);
        this.todoInput.value = '';
        this.saveToStorage();
        this.render();
    }
    
    toggleTodo(id) {
        const todo = this.todos.find(t => t.id === id);
        if (todo) {
            todo.completed = !todo.completed;
            this.saveToStorage();
            this.render();
        }
    }
    
    deleteTodo(id) {
        this.todos = this.todos.filter(t => t.id !== id);
        this.saveToStorage();
        this.render();
    }
    
    clearCompleted() {
        this.todos = this.todos.filter(t => !t.completed);
        this.saveToStorage();
        this.render();
    }
    
    render() {
        // Clear current list
        this.todoListEl.innerHTML = '';
        
        // Show empty state or render todos
        if (this.todos.length === 0) {
            this.todoListEl.innerHTML = '<li class="empty-state">No tasks yet. Add one above!</li>';
        } else {
            this.todos.forEach(todo => {
                const li = this.createTodoElement(todo);
                this.todoListEl.appendChild(li);
            });
        }
        
        // Update task count
        this.updateTaskCount();
    }
    
    createTodoElement(todo) {
        const li = document.createElement('li');
        li.className = `todo-item${todo.completed ? ' completed' : ''}`;
        
        // Checkbox
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.className = 'todo-checkbox';
        checkbox.checked = todo.completed;
        checkbox.addEventListener('change', () => this.toggleTodo(todo.id));
        
        // Text
        const span = document.createElement('span');
        span.className = 'todo-text';
        span.textContent = todo.text;
        
        // Delete button
        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete-btn';
        deleteBtn.textContent = 'Delete';
        deleteBtn.addEventListener('click', () => this.deleteTodo(todo.id));
        
        li.appendChild(checkbox);
        li.appendChild(span);
        li.appendChild(deleteBtn);
        
        return li;
    }
    
    updateTaskCount() {
        const totalTasks = this.todos.length;
        const completedTasks = this.todos.filter(t => t.completed).length;
        const activeTasks = totalTasks - completedTasks;
        
        this.taskCount.textContent = `${activeTasks} active task${activeTasks !== 1 ? 's' : ''}, ${completedTasks} completed`;
    }
    
    saveToStorage() {
        localStorage.setItem('todos', JSON.stringify(this.todos));
    }
    
    loadFromStorage() {
        const stored = localStorage.getItem('todos');
        return stored ? JSON.parse(stored) : [];
    }
}

// Initialize the app when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new TodoList();
});
