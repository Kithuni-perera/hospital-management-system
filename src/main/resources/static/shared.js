// shared.js — Common utilities for all pages
const API = '/api';

// ── AUTH ──
function getToken() { return localStorage.getItem('token'); }
function getUser()  { return JSON.parse(localStorage.getItem('user') || '{}'); }

function requireAuth() {
  if (!getToken()) { window.location.href = 'login.html'; return false; }
  return true;
}

function logout() {
  localStorage.clear();
  window.location.href = 'login.html';
}

// ── API CALLS ──
async function apiGet(path) {
  const res = await fetch(`${API}${path}`, {
    headers: { 'Authorization': `Bearer ${getToken()}` }
  });
  if (res.status === 401) { logout(); return null; }
  return res.json();
}

async function apiPost(path, body) {
  const res = await fetch(`${API}${path}`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${getToken()}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(body)
  });
  if (res.status === 401) { logout(); return null; }
  return res.json();
}

async function apiPut(path, body) {
  const res = await fetch(`${API}${path}`, {
    method: 'PUT',
    headers: {
      'Authorization': `Bearer ${getToken()}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(body)
  });
  if (res.status === 401) { logout(); return null; }
  return res.json();
}

async function apiPatch(path, body = null) {
  const opts = {
    method: 'PATCH',
    headers: { 'Authorization': `Bearer ${getToken()}` }
  };
  if (body) {
    opts.headers['Content-Type'] = 'application/json';
    opts.body = JSON.stringify(body);
  }
  const res = await fetch(`${API}${path}`, opts);
  if (res.status === 401) { logout(); return null; }
  return res.json();
}

async function apiDelete(path) {
  const res = await fetch(`${API}${path}`, {
    method: 'DELETE',
    headers: { 'Authorization': `Bearer ${getToken()}` }
  });
  if (res.status === 401) { logout(); return null; }
  return res.json();
}

// ── TOAST ──
function showToast(msg, type = 'success') {
  let t = document.getElementById('toast');
  if (!t) {
    t = document.createElement('div');
    t.id = 'toast';
    t.className = 'toast';
    document.body.appendChild(t);
  }
  t.className = `toast ${type}`;
  t.innerHTML = `<span>${type === 'success' ? '✅' : '❌'}</span> ${msg}`;
  t.classList.add('show');
  setTimeout(() => t.classList.remove('show'), 3500);
}

// ── MODAL ──
function openModal(id) {
  document.getElementById(id).classList.add('open');
}

function closeModal(id) {
  document.getElementById(id).classList.remove('open');
}

// ── SIDEBAR ──
function buildSidebar(activePage) {
  const user = getUser();
  const initials = ((user.fullName || 'Admin').split(' ').map(w => w[0]).join('').toUpperCase()).slice(0,2);

  const navItems = [
    { href: 'dashboard.html', icon: '📊', label: 'Dashboard', section: null },
    { section: 'Management' },
    { href: 'departments.html', icon: '🏢', label: 'Departments' },
    { href: 'doctors.html',     icon: '👨‍⚕️', label: 'Doctors' },
    { href: 'patients.html',    icon: '🧑‍🤝‍🧑', label: 'Patients' },
    { href: 'appointments.html',icon: '📅', label: 'Appointments' },
    { section: 'Facilities' },
    { href: 'rooms.html',       icon: '🏠', label: 'Rooms' },
    { href: 'beds.html',        icon: '🛏️', label: 'Beds' },
    { section: 'Finance' },
    { href: 'billing.html',     icon: '💰', label: 'Billing' },
    { section: 'Admin' },
    { href: 'users.html',       icon: '👤', label: 'Users' },
    { href: 'reports.html',     icon: '📈', label: 'Reports' },
  ];

  let html = `
    <div class="sidebar">
      <div class="sidebar-logo">
        <div class="logo-icon">🏥</div>
        <span>MediCore HMS</span>
      </div>
      <nav class="sidebar-nav">`;

  for (const item of navItems) {
    if (item.section) {
      html += `<div class="nav-section">${item.section}</div>`;
    } else {
      const active = activePage === item.href ? 'active' : '';
      html += `<a class="nav-item ${active}" href="${item.href}">
        <span class="icon">${item.icon}</span> ${item.label}
      </a>`;
    }
  }

  html += `</nav>
      <div class="sidebar-footer">
        <div class="user-info">
          <div class="user-avatar">${initials}</div>
          <div>
            <div class="user-name">${user.fullName || 'Admin'}</div>
            <div class="user-role">${user.role || 'ADMIN'}</div>
          </div>
        </div>
        <button class="btn-logout" onclick="logout()">Sign Out</button>
      </div>
    </div>`;

  document.getElementById('sidebar-container').innerHTML = html;
}

// ── HELPERS ──
function fmt(val, fallback = '—') {
  return val !== null && val !== undefined && val !== '' ? val : fallback;
}

function fmtDate(d) {
  if (!d) return '—';
  return new Date(d).toLocaleDateString('en-GB', { day:'2-digit', month:'short', year:'numeric' });
}

function fmtCurrency(n) {
  if (n == null) return '—';
  return 'Rs. ' + Number(n).toLocaleString('en-LK', { minimumFractionDigits: 2 });
}

function statusBadge(status) {
  const map = {
    SCHEDULED: 'badge-teal', CONFIRMED: 'badge-green', COMPLETED: 'badge-green',
    CANCELLED: 'badge-red', NO_SHOW: 'badge-orange',
    AVAILABLE: 'badge-green', OCCUPIED: 'badge-red',
    MAINTENANCE: 'badge-orange', RESERVED: 'badge-gold',
    PENDING: 'badge-gold', PAID: 'badge-green',
    PARTIALLY_PAID: 'badge-orange', REFUNDED: 'badge-teal',
    ACTIVE: 'badge-green', INACTIVE: 'badge-red',
    ADMIN: 'badge-teal', DOCTOR: 'badge-green',
    NURSE: 'badge-gold', RECEPTIONIST: 'badge-orange'
  };
  return `<span class="badge ${map[status] || 'badge-gray'}">${status}</span>`;
}

function confirmDelete(name, onConfirm) {
  if (confirm(`Delete "${name}"? This cannot be undone.`)) onConfirm();
}
