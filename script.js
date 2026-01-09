const API = "http://localhost:8080";

/* ================= AUTH CHECK (BLOCK LOGIN IF ALREADY LOGGED IN) ================= */
(async function () {
  const r = await fetch(`${API}/api/me`, {
    credentials: "include"
  });

  if (r.ok) {
    location.replace("dashboard.html");
  }
})();

async function login() {
  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value.trim();

  if (!username || !password) {
    alert("Username and password required");
    return;
  }

  const response = await fetch(`${API}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({ username, password })
  });

  if (response.ok) {
    window.location.href = "dashboard.html";
  } else {
    alert("Invalid username or password");
  }
}

async function signup() {
  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value.trim();

  if (!username || !password) {
    alert("Username and password required");
    return;
  }

  const response = await fetch(`${API}/api/auth/signup`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  });

  const msg = await response.text();
  alert(msg);
}
