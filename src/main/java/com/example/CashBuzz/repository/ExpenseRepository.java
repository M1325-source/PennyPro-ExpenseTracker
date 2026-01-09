package com.example.CashBuzz.repository;

import com.example.CashBuzz.entity.Expense;
import com.example.CashBuzz.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Filter by category
    List<Expense> findByCategory(ExpenseCategory category);

    // Filter by date
    List<Expense> findByExpenseDate(LocalDate expenseDate);

    // Filter by amount greater than
    List<Expense> findByAmountGreaterThan(double amount);
    // DESCENDING order
    List<Expense> findAllByOrderByAmountDesc();
}