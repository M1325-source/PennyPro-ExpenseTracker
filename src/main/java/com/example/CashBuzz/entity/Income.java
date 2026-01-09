package com.example.CashBuzz.entity;



import com.example.CashBuzz.enums.IncomeSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "income")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String incomeDescription;

    @Enumerated(EnumType.STRING)
    private IncomeSource source;

    private double amount;

    private LocalDate incomeDate;
}