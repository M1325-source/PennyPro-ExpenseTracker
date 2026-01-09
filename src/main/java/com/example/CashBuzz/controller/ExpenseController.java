package com.example.CashBuzz.controller;

import com.example.CashBuzz.entity.Expense;
import com.example.CashBuzz.enums.ExpenseCategory;   // ✅ IMPORT THIS
import com.example.CashBuzz.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    // ➕ ADD EXPENSE
    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    // 📄 GET ALL EXPENSES
    @GetMapping("/all")
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // 🔽 SORT BY AMOUNT DESC
    @GetMapping("/sort/amount/desc")
    public List<Expense> sortByAmountDesc() {
        return expenseRepository.findAllByOrderByAmountDesc();
    }

    // 🎯 FILTER BY CATEGORY (✅ FIXED)
    @GetMapping("/filter/category")
    public List<Expense> filterByCategory(
            @RequestParam ExpenseCategory category
    ) {
        return expenseRepository.findByCategory(category);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseRepository.deleteById(id);
        return "Expense deleted";
    }

}