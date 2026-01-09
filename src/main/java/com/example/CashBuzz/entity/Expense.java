package com.example.CashBuzz.entity;

import com.example.CashBuzz.enums.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")   // ✅ PEHLE YE THA – ISI KO USE KARNA HAI
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expenseDescription;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    private double amount;

    private LocalDate expenseDate;
}