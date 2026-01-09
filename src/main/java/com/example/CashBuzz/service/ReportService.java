package com.example.CashBuzz.service;

import com.example.CashBuzz.entity.Expense;
import com.example.CashBuzz.entity.Income;
import com.example.CashBuzz.repository.ExpenseRepository;
import com.example.CashBuzz.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    public Map<String, Double> getTotalPnL() {

        List<Expense> expenses = expenseRepository.findAll();
        List<Income> incomes = incomeRepository.findAll();

        double totalExpense = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double totalIncome = incomes.stream()
                .mapToDouble(Income::getAmount)
                .sum();

        double pnl = totalIncome - totalExpense;

        return Map.of(
                "totalIncome", totalIncome,
                "totalExpense", totalExpense,
                "profitOrLoss", pnl
        );
    }
}