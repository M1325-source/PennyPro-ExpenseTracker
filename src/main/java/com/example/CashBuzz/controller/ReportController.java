package com.example.CashBuzz.controller;

import com.example.CashBuzz.entity.Expense;
import com.example.CashBuzz.entity.Income;
import com.example.CashBuzz.enums.ExpenseCategory;
import com.example.CashBuzz.enums.IncomeSource;
import com.example.CashBuzz.repository.ExpenseRepository;
import com.example.CashBuzz.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    // 🔽 SORT EXPENSE BY AMOUNT
    @GetMapping("/expense/sort/amount")
    public List<Expense> sortExpenseByAmount() {
        return expenseRepository.findAll(Sort.by("amount"));
    }

    // 🔽 SORT INCOME BY AMOUNT
    @GetMapping("/income/sort/amount")
    public List<Income> sortIncomeByAmount() {
        return incomeRepository.findAll(Sort.by("amount"));
    }

    // 🎯 FILTER EXPENSE BY CATEGORY
    @GetMapping("/expense/filter/category")
    public List<Expense> filterExpenseByCategory(@RequestParam ExpenseCategory category) {
        return expenseRepository.findByCategory(category);
    }

    // 🎯 FILTER INCOME BY SOURCE
    @GetMapping("/income/filter/source")
    public List<Income> filterIncomeBySource(@RequestParam IncomeSource source) {
        return incomeRepository.findBySource(source);
    }

    // 📅 FILTER EXPENSE BY DATE
    @GetMapping("/expense/filter/date")
    public List<Expense> filterExpenseByDate(@RequestParam String date) {
        return expenseRepository.findByExpenseDate(LocalDate.parse(date));
    }

    // 💰 FILTER INCOME BY AMOUNT
    @GetMapping("/income/filter/amount")
    public List<Income> filterIncomeByAmount(@RequestParam double amount) {
        return incomeRepository.findByAmountGreaterThan(amount);
    }

    // ✅ TOTAL PNL
    @GetMapping("/pnl")
    public Map<String, Double> calculatePNL() {

        List<Income> incomes = incomeRepository.findAll();
        List<Expense> expenses = expenseRepository.findAll();

        double totalIncome = incomes.stream()
                .mapToDouble(Income::getAmount)
                .sum();

        double totalExpense = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double pnl = totalIncome - totalExpense;

        Map<String, Double> response = new HashMap<>();
        response.put("totalIncome", totalIncome);
        response.put("totalExpense", totalExpense);
        response.put("PNL", pnl);

        return response;
    }
}