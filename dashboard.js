const API = "http://localhost:8080";

let lineChart = null;
let donutChart = null;

/* ================= AUTH ================= */
async function checkAuth() {
  const r = await fetch(`${API}/api/report/pnl`, { credentials: "include" });
  if (!r.ok) location.href = "auth.html";
}


/* ================= LOAD ================= */
async function loadDashboard() {
  await checkAuth();
  const expenses = await loadExpenses();
  const incomes = await loadIncome();
  await loadPnL();
  drawCharts(expenses, incomes);
}

/* ================= PNL ================= */
async function loadPnL() {
  const r = await fetch(`${API}/api/report/pnl`, { credentials: "include" });
  const d = await r.json();

  totalIncome.innerText = `₹ ${d.totalIncome}`;
  totalExpense.innerText = `₹ ${d.totalExpense}`;
  pnl.innerText = `₹ ${d.PNL}`;
}

/* ================= EXPENSE ================= */
async function loadExpenses(url = "/api/expenses/all") {
  const r = await fetch(API + url, { credentials: "include" });
  const d = await r.json();

  expenseTable.innerHTML = "";
  d.forEach(e => {
    expenseTable.innerHTML += `
      <tr>
        <td>${e.expenseDescription}</td>
        <td>${e.category}</td>
        <td>₹ ${e.amount}</td>
        <td>
          <button class="action-btn delete"
            onclick="deleteExpense(${e.id})">
            Delete
          </button>
        </td>
      </tr>`;
  });

  updateCount();
  return d;
}

function deleteExpense(id) {
  fetch(`${API}/api/expenses/delete/${id}`, {
    method: "DELETE",
    credentials: "include"
  }).then(loadDashboard);
}

/* ================= INCOME ================= */
async function loadIncome(url = "/api/income/all") {
  const r = await fetch(API + url, { credentials: "include" });
  const d = await r.json();

  incomeTable.innerHTML = "";
  d.forEach(i => {
    incomeTable.innerHTML += `
      <tr>
        <td>${i.incomeDescription}</td>
        <td>${i.source}</td>
        <td>₹ ${i.amount}</td>
        <td>
          <button class="action-btn delete"
            onclick="deleteIncome(${i.id})">
            Delete
          </button>
        </td>
      </tr>`;
  });

  updateCount();
  return d;
}

function deleteIncome(id) {
  fetch(`${API}/api/income/delete/${id}`, {
    method: "DELETE",
    credentials: "include"
  }).then(loadDashboard);
}

/* ================= MODALS ================= */
function openExpenseModal() { expenseModal.style.display = "flex"; }
function closeExpenseModal() { expenseModal.style.display = "none"; }

function openIncomeModal() { incomeModal.style.display = "flex"; }
function closeIncomeModal() { incomeModal.style.display = "none"; }

/* ================= SAVE ================= */
function saveExpense() {
  fetch(`${API}/api/expenses/add`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({
      expenseDescription: expDesc.value,
      category: expCat.value,
      amount: expAmt.value,
      expenseDate: expDate.value
    })
  }).then(() => {
    closeExpenseModal();
    loadDashboard();
  });
}

function saveIncome() {
  fetch(`${API}/api/income/add`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({
      incomeDescription: incDesc.value,
      source: incSrc.value,
      amount: incAmt.value,
      incomeDate: incDate.value
    })
  }).then(() => {
    closeIncomeModal();
    loadDashboard();
  });
}

/* ================= SORT / FILTER ================= */
function sortExpenses() {
  loadExpenses("/api/expenses/sort/amount/desc");
}

function sortIncome() {
  loadIncome("/api/income/sort/amount/desc");
}

function filterExpense(c) {
  c
    ? loadExpenses(`/api/expenses/filter/category?category=${c}`)
    : loadExpenses();
}

function filterIncome(s) {
  s
    ? loadIncome(`/api/report/income/filter/source?source=${s}`)
    : loadIncome();
}

/* ================= CHARTS (FIXED) ================= */
function drawCharts(expenses, incomes) {

  const lineCanvas = document.getElementById("lineChart");
  const donutCanvas = document.getElementById("donutChart");

  if (!lineCanvas || !donutCanvas) return;

  const labels = [...new Set([
    ...expenses.map(e => e.expenseDate),
    ...incomes.map(i => i.incomeDate)
  ])].sort();

  const expenseData = labels.map(date =>
    expenses
      .filter(e => e.expenseDate === date)
      .reduce((sum, e) => sum + Number(e.amount), 0)
  );

  const incomeData = labels.map(date =>
    incomes
      .filter(i => i.incomeDate === date)
      .reduce((sum, i) => sum + Number(i.amount), 0)
  );

  if (lineChart) lineChart.destroy();
  if (donutChart) donutChart.destroy();

  lineChart = new Chart(lineCanvas, {
    type: "line",
    data: {
      labels,
      datasets: [
        {
          label: "Income",
          data: incomeData,
          borderWidth: 3,
          tension: 0.4
        },
        {
          label: "Expense",
          data: expenseData,
          borderWidth: 3,
          tension: 0.4
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  });

  donutChart = new Chart(donutCanvas, {
    type: "doughnut",
    data: {
      labels: ["Income", "Expense"],
      datasets: [{
        data: [
          incomeData.reduce((a, b) => a + b, 0),
          expenseData.reduce((a, b) => a + b, 0)
        ]
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  });
}

/* ================= MISC ================= */
function updateCount() {
  totalRecords.innerText =
    expenseTable.children.length + incomeTable.children.length;
}

function logout() {
  location.href = `${API}/logout`;
}

/* ================= INIT ================= */
loadDashboard();
