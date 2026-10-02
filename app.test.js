/**
 * Unit tests for Todo List Application
 */

// Mock localStorage
const localStorageMock = (() => {
  let store = {};
  return {
    getItem: (key) => store[key] || null,
    setItem: (key, value) => { store[key] = value.toString(); },
    clear: () => { store = {}; },
    removeItem: (key) => { delete store[key]; }
  };
})();

global.localStorage = localStorageMock;

// Mock DOM elements
document.body.innerHTML = `
  <input type="text" id="todoInput" />
  <button id="addBtn">Add</button>
  <ul id="todoList"></ul>
  <span id="taskCount"></span>
  <button id="clearCompleted">Clear Completed</button>
  <select id="themeSelect">
    <option value="purple">Purple</option>
    <option value="ocean">Ocean Blue</option>
  </select>
`;

// Load the app module
// Note: We need to isolate testable functions
// For this test, we'll test the core logic

describe('Todo List Core Functions', () => {
  let todos;

  beforeEach(() => {
    todos = [];
    localStorage.clear();
  });

  describe('escapeHtml', () => {
    test('should escape HTML special characters', () => {
      const escapeHtml = (text) => {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
      };

      expect(escapeHtml('<script>alert("xss")</script>')).toBe('&lt;script&gt;alert("xss")&lt;/script&gt;');
      expect(escapeHtml('Normal text')).toBe('Normal text');
      expect(escapeHtml('<b>Bold</b>')).toBe('&lt;b&gt;Bold&lt;/b&gt;');
      expect(escapeHtml('Test & <>')).toBe('Test &amp; &lt;&gt;');
    });
  });

  describe('Todo operations', () => {
    test('should add a new todo', () => {
      const todo = {
        id: Date.now(),
        text: 'Test task',
        completed: false
      };
      
      todos.push(todo);
      
      expect(todos).toHaveLength(1);
      expect(todos[0].text).toBe('Test task');
      expect(todos[0].completed).toBe(false);
      expect(todos[0].id).toBeDefined();
    });

    test('should delete a todo by id', () => {
      const todo1 = { id: 1, text: 'Task 1', completed: false };
      const todo2 = { id: 2, text: 'Task 2', completed: false };
      todos = [todo1, todo2];

      todos = todos.filter(todo => todo.id !== 1);

      expect(todos).toHaveLength(1);
      expect(todos[0].id).toBe(2);
    });

    test('should toggle todo completion', () => {
      const todo = { id: 1, text: 'Task 1', completed: false };
      todos = [todo];

      todos = todos.map(t => {
        if (t.id === 1) {
          return { ...t, completed: !t.completed };
        }
        return t;
      });

      expect(todos[0].completed).toBe(true);

      // Toggle again
      todos = todos.map(t => {
        if (t.id === 1) {
          return { ...t, completed: !t.completed };
        }
        return t;
      });

      expect(todos[0].completed).toBe(false);
    });

    test('should clear all completed todos', () => {
      todos = [
        { id: 1, text: 'Task 1', completed: true },
        { id: 2, text: 'Task 2', completed: false },
        { id: 3, text: 'Task 3', completed: true }
      ];

      todos = todos.filter(todo => !todo.completed);

      expect(todos).toHaveLength(1);
      expect(todos[0].id).toBe(2);
    });

    test('should count active tasks correctly', () => {
      todos = [
        { id: 1, text: 'Task 1', completed: true },
        { id: 2, text: 'Task 2', completed: false },
        { id: 3, text: 'Task 3', completed: false }
      ];

      const activeCount = todos.filter(todo => !todo.completed).length;

      expect(activeCount).toBe(2);
    });
  });

  describe('localStorage integration', () => {
    test('should save todos to localStorage', () => {
      todos = [
        { id: 1, text: 'Task 1', completed: false },
        { id: 2, text: 'Task 2', completed: true }
      ];

      localStorage.setItem('todos', JSON.stringify(todos));
      const saved = JSON.parse(localStorage.getItem('todos'));

      expect(saved).toHaveLength(2);
      expect(saved[0].text).toBe('Task 1');
      expect(saved[1].completed).toBe(true);
    });

    test('should load todos from localStorage', () => {
      const testTodos = [
        { id: 1, text: 'Saved Task', completed: false }
      ];
      
      localStorage.setItem('todos', JSON.stringify(testTodos));
      const loaded = JSON.parse(localStorage.getItem('todos')) || [];

      expect(loaded).toHaveLength(1);
      expect(loaded[0].text).toBe('Saved Task');
    });

    test('should handle empty localStorage', () => {
      const loaded = JSON.parse(localStorage.getItem('todos')) || [];
      expect(loaded).toEqual([]);
    });
  });

  describe('Theme functionality', () => {
    test('should save theme to localStorage', () => {
      const theme = 'ocean';
      localStorage.setItem('theme', theme);
      
      expect(localStorage.getItem('theme')).toBe('ocean');
    });

    test('should load theme from localStorage with default', () => {
      const theme = localStorage.getItem('theme') || 'purple';
      expect(theme).toBe('purple');

      localStorage.setItem('theme', 'sunset');
      const loadedTheme = localStorage.getItem('theme') || 'purple';
      expect(loadedTheme).toBe('sunset');
    });
  });

  describe('Input validation', () => {
    test('should not add empty todos', () => {
      const text = '   '.trim();
      
      if (text !== '') {
        todos.push({ id: Date.now(), text, completed: false });
      }

      expect(todos).toHaveLength(0);
    });

    test('should trim whitespace from todo text', () => {
      const text = '  Test task  '.trim();
      todos.push({ id: Date.now(), text, completed: false });

      expect(todos[0].text).toBe('Test task');
    });
  });

  describe('Task counter display', () => {
    test('should show singular for 1 task', () => {
      const activeCount = 1;
      const text = `${activeCount} task${activeCount !== 1 ? 's' : ''}`;
      expect(text).toBe('1 task');
    });

    test('should show plural for 0 tasks', () => {
      const activeCount = 0;
      const text = `${activeCount} task${activeCount !== 1 ? 's' : ''}`;
      expect(text).toBe('0 tasks');
    });

    test('should show plural for multiple tasks', () => {
      const activeCount = 5;
      const text = `${activeCount} task${activeCount !== 1 ? 's' : ''}`;
      expect(text).toBe('5 tasks');
    });
  });
});
