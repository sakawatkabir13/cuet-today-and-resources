// Global JavaScript functions for CUET Today

// Mobile navigation toggle
const hamburger = document.getElementById('hamburger');
const navMenu = document.getElementById('nav-menu');

if (hamburger && navMenu) {
    hamburger.addEventListener('click', function() {
        navMenu.classList.toggle('active');
    });

    // Close mobile menu when clicking on a link
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', () => {
            navMenu.classList.remove('active');
        });
    });
}

// Check user session and update UI accordingly
async function checkUserSession() {
    try {
        const response = await fetch('/api/users/current');
        const data = await response.json();
        
        if (data.success) {
            // User is logged in
            updateNavigationForLoggedInUser();
            return data.user;
        } else {
            // User is not logged in
            updateNavigationForLoggedOutUser();
            return null;
        }
    } catch (error) {
        console.error('Error checking session:', error);
        updateNavigationForLoggedOutUser();
        return null;
    }
}

// Update navigation for logged in user
function updateNavigationForLoggedInUser() {
    const loginItem = document.getElementById('login-item');
    const registerItem = document.getElementById('register-item');
    const logoutItem = document.getElementById('logout-item');
    
    if (loginItem) loginItem.style.display = 'none';
    if (registerItem) registerItem.style.display = 'none';
    if (logoutItem) logoutItem.style.display = 'block';
}

// Update navigation for logged out user
function updateNavigationForLoggedOutUser() {
    const loginItem = document.getElementById('login-item');
    const registerItem = document.getElementById('register-item');
    const logoutItem = document.getElementById('logout-item');
    
    if (loginItem) loginItem.style.display = 'block';
    if (registerItem) registerItem.style.display = 'block';
    if (logoutItem) logoutItem.style.display = 'none';
}

// Logout function
async function logout() {
    try {
        const response = await fetch('/api/users/logout', {
            method: 'POST'
        });
        
        const data = await response.json();
        
        if (data.success) {
            // Redirect to home page
            window.location.href = '/';
        } else {
            alert('Logout failed');
        }
    } catch (error) {
        console.error('Logout error:', error);
        alert('An error occurred during logout');
    }
}

// Utility function to format dates
function formatDate(dateString) {
    const options = { 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    return new Date(dateString).toLocaleDateString('en-US', options);
}

// Utility function to truncate text
function truncateText(text, maxLength) {
    if (text.length <= maxLength) {
        return text;
    }
    return text.substr(0, maxLength) + '...';
}

// Show loading spinner
function showLoading(containerId) {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = '<div class="loading">Loading...</div>';
    }
}

// Show error message
function showError(containerId, message) {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = `<div class="error">${message}</div>`;
    }
}

// Show no data message
function showNoData(containerId, message) {
    const container = document.getElementById(containerId);
    if (container) {
        container.innerHTML = `<div class="no-data">${message}</div>`;
    }
}

// Validate email format
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Validate URL format
function isValidURL(url) {
    try {
        new URL(url);
        return true;
    } catch {
        return false;
    }
}

// Show notification
function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.innerHTML = `
        <span>${message}</span>
        <button class="notification-close">&times;</button>
    `;
    
    // Add styles
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        border-radius: 5px;
        color: white;
        z-index: 1001;
        max-width: 300px;
        animation: slideIn 0.3s ease;
    `;
    
    // Set background color based on type
    switch (type) {
        case 'success':
            notification.style.backgroundColor = '#27ae60';
            break;
        case 'error':
            notification.style.backgroundColor = '#e74c3c';
            break;
        case 'warning':
            notification.style.backgroundColor = '#f39c12';
            break;
        default:
            notification.style.backgroundColor = '#3498db';
    }
    
    // Add animation keyframes if not already added
    if (!document.getElementById('notification-styles')) {
        const style = document.createElement('style');
        style.id = 'notification-styles';
        style.textContent = `
            @keyframes slideIn {
                from { transform: translateX(100%); opacity: 0; }
                to { transform: translateX(0); opacity: 1; }
            }
            .notification-close {
                background: none;
                border: none;
                color: white;
                font-size: 18px;
                cursor: pointer;
                margin-left: 10px;
                padding: 0;
            }
        `;
        document.head.appendChild(style);
    }
    
    // Add to document
    document.body.appendChild(notification);
    
    // Add close functionality
    const closeButton = notification.querySelector('.notification-close');
    closeButton.addEventListener('click', () => {
        notification.remove();
    });
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (notification.parentNode) {
            notification.remove();
        }
    }, 5000);
}

// Confirm dialog
function confirmAction(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

// Initialize common functionality
document.addEventListener('DOMContentLoaded', function() {
    // Check user session on page load
    checkUserSession();
    
    // Handle modal close when clicking outside
    window.addEventListener('click', function(event) {
        const modals = document.querySelectorAll('.modal');
        modals.forEach(modal => {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    });
    
    // Handle escape key for modals
    document.addEventListener('keydown', function(event) {
        if (event.key === 'Escape') {
            const modals = document.querySelectorAll('.modal');
            modals.forEach(modal => {
                if (modal.style.display === 'block') {
                    modal.style.display = 'none';
                }
            });
        }
    });
});

// Export functions for use in other scripts
window.CuetToday = {
    checkUserSession,
    logout,
    formatDate,
    truncateText,
    showLoading,
    showError,
    showNoData,
    isValidEmail,
    isValidURL,
    showNotification,
    confirmAction
};