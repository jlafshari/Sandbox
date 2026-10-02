// Get DOM elements
const todoInput = document.getElementById('todoInput');
const addBtn = document.getElementById('addBtn');
const todoList = document.getElementById('todoList');
const taskCount = document.getElementById('taskCount');
const clearCompletedBtn = document.getElementById('clearCompleted');
const themeSelect = document.getElementById('themeSelect');

// Load todos from localStorage
let todos = JSON.parse(localStorage.getItem('todos')) || [];

// Load theme from localStorage
let currentTheme = localStorage.getItem('theme') || 'purple';

// Current filter state
let currentFilter = 'all';

// Initialize the app
function init() {
    applyTheme(currentTheme);
    themeSelect.value = currentTheme;
    renderTodos();
    updateTaskCount();
}

// Apply theme
function applyTheme(theme) {
    // Remove all theme classes
    document.body.className = '';
    // Add selected theme class
    document.body.classList.add(`theme-${theme}`);
    currentTheme = theme;
    localStorage.setItem('theme', theme);
}

// Theme change handler
function handleThemeChange(e) {
    applyTheme(e.target.value);
}

// Add new todo
function addTodo() {
    const text = todoInput.value.trim();
    
    if (text === '') {
        return;
    }
    
    const todo = {
        id: Date.now(),
        text: text,
        completed: false
    };
    
    todos.push(todo);
    saveTodos();
    renderTodos();
    updateTaskCount();
    todoInput.value = '';
}

// Delete todo
function deleteTodo(id) {
    todos = todos.filter(todo => todo.id !== id);
    saveTodos();
    renderTodos();
    updateTaskCount();
}

// Toggle todo completion
function toggleTodo(id) {
    todos = todos.map(todo => {
        if (todo.id === id) {
            return { ...todo, completed: !todo.completed };
        }
        return todo;
    });
    saveTodos();
    renderTodos();
    updateTaskCount();
}

// Clear completed todos
function clearCompleted() {
    todos = todos.filter(todo => !todo.completed);
    saveTodos();
    renderTodos();
    updateTaskCount();
}

// Get filtered todos based on current filter
function getFilteredTodos() {
    switch(currentFilter) {
        case 'active':
            return todos.filter(todo => !todo.completed);
        case 'completed':
            return todos.filter(todo => todo.completed);
        default:
            return todos;
    }
}

// Set active filter
function setFilter(filter) {
    currentFilter = filter;
    
    // Update active state on buttons
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.classList.remove('active');
        if (btn.dataset.filter === filter) {
            btn.classList.add('active');
        }
    });
    
    renderTodos();
}

// Render todos to the DOM
function renderTodos() {
    todoList.innerHTML = '';
    
    const filteredTodos = getFilteredTodos();
    
    if (filteredTodos.length === 0) {
        let emptyMessage = 'No tasks yet. Add one above!';
        if (currentFilter === 'active' && todos.length > 0) {
            emptyMessage = 'No active tasks!';
        } else if (currentFilter === 'completed' && todos.length > 0) {
            emptyMessage = 'No completed tasks yet!';
        }
        todoList.innerHTML = `<div class="empty-state">${emptyMessage}</div>`;
        return;
    }
    
    filteredTodos.forEach(todo => {
        const li = document.createElement('li');
        li.className = `todo-item ${todo.completed ? 'completed' : ''}`;
        
        li.innerHTML = `
            <input type="checkbox" class="todo-checkbox" ${todo.completed ? 'checked' : ''} data-id="${todo.id}">
            <span class="todo-text">${escapeHtml(todo.text)}</span>
            <button class="delete-btn" data-id="${todo.id}">Delete</button>
        `;
        
        todoList.appendChild(li);
    });
    
    // Add event listeners to checkboxes
    document.querySelectorAll('.todo-checkbox').forEach(checkbox => {
        checkbox.addEventListener('change', (e) => {
            toggleTodo(Number(e.target.dataset.id));
        });
    });
    
    // Add event listeners to delete buttons
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            deleteTodo(Number(e.target.dataset.id));
        });
    });
}

// Update task count
function updateTaskCount() {
    const activeCount = todos.filter(todo => !todo.completed).length;
    taskCount.textContent = `${activeCount} task${activeCount !== 1 ? 's' : ''}`;
}

// Save todos to localStorage
function saveTodos() {
    localStorage.setItem('todos', JSON.stringify(todos));
}

// Escape HTML to prevent XSS
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Event listeners
addBtn.addEventListener('click', addTodo);
todoInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        addTodo();
    }
});
clearCompletedBtn.addEventListener('click', clearCompleted);
themeSelect.addEventListener('change', handleThemeChange);

// Filter button event listeners
document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
        setFilter(e.target.dataset.filter);
    });
});

// Initialize the app
init();
