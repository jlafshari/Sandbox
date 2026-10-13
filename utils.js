const leftPad = require('left-pad');

/**
 * Format a task ID with zero-padding to ensure consistent width.
 * Useful for displaying task IDs in a uniform format.
 * 
 * @param {number} id - The task ID (typically a timestamp)
 * @param {number} width - The desired width (default: 15)
 * @returns {string} The padded task ID
 */
function formatTaskId(id, width = 15) {
  return leftPad(id.toString(), width, '0');
}

/**
 * Format a counter with zero-padding.
 * Useful for displaying task counts like "Task 001 of 100"
 * 
 * @param {number} count - The number to format
 * @param {number} width - The desired width (default: 3)
 * @returns {string} The padded count
 */
function formatCounter(count, width = 3) {
  return leftPad(count.toString(), width, '0');
}

/**
 * Format a timestamp as HH:MM:SS with zero-padding.
 * 
 * @param {Date} date - The date object to format
 * @returns {string} The formatted time string
 */
function formatTime(date) {
  const hours = leftPad(date.getHours().toString(), 2, '0');
  const minutes = leftPad(date.getMinutes().toString(), 2, '0');
  const seconds = leftPad(date.getSeconds().toString(), 2, '0');
  return `${hours}:${minutes}:${seconds}`;
}

/**
 * Format a date as YYYY-MM-DD with zero-padding.
 * 
 * @param {Date} date - The date object to format
 * @returns {string} The formatted date string
 */
function formatDate(date) {
  const year = date.getFullYear();
  const month = leftPad((date.getMonth() + 1).toString(), 2, '0');
  const day = leftPad(date.getDate().toString(), 2, '0');
  return `${year}-${month}-${day}`;
}

module.exports = {
  formatTaskId,
  formatCounter,
  formatTime,
  formatDate
};
