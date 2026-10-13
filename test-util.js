const { formatTaskId, formatCounter, formatTime, formatDate } = require('./utils');

console.log('=== Testing left-pad utility functions ===\n');

// Test formatTaskId
console.log('1. Testing formatTaskId():');
const taskId1 = Date.now();
const taskId2 = 12345;
console.log(`   Task ID ${taskId1} => ${formatTaskId(taskId1)}`);
console.log(`   Task ID ${taskId2} => ${formatTaskId(taskId2)}`);
console.log(`   Task ID ${taskId2} (width 10) => ${formatTaskId(taskId2, 10)}`);
console.log();

// Test formatCounter
console.log('2. Testing formatCounter():');
console.log(`   Counter 1 => ${formatCounter(1)}`);
console.log(`   Counter 42 => ${formatCounter(42)}`);
console.log(`   Counter 999 => ${formatCounter(999)}`);
console.log(`   Counter 5 (width 5) => ${formatCounter(5, 5)}`);
console.log();

// Test formatTime
console.log('3. Testing formatTime():');
const now = new Date();
const morning = new Date(2024, 0, 1, 9, 5, 3);
const evening = new Date(2024, 0, 1, 23, 45, 59);
console.log(`   Current time: ${formatTime(now)}`);
console.log(`   Morning time: ${formatTime(morning)}`);
console.log(`   Evening time: ${formatTime(evening)}`);
console.log();

// Test formatDate
console.log('4. Testing formatDate():');
console.log(`   Current date: ${formatDate(now)}`);
console.log(`   Specific date: ${formatDate(new Date(2024, 0, 5))}`);
console.log(`   Specific date: ${formatDate(new Date(2024, 11, 25))}`);
console.log();

// Practical example: formatting a todo list
console.log('5. Practical Example - Todo List with Formatted IDs:');
const todos = [
  { id: Date.now(), text: 'Learn left-pad', completed: false },
  { id: Date.now() + 1, text: 'Create utils', completed: true },
  { id: Date.now() + 2, text: 'Test the code', completed: false }
];

todos.forEach((todo, index) => {
  const status = todo.completed ? '✓' : ' ';
  console.log(`   [${status}] ${formatCounter(index + 1)}. [ID: ${formatTaskId(todo.id)}] ${todo.text}`);
});
console.log();

console.log('=== All tests completed successfully! ===');
