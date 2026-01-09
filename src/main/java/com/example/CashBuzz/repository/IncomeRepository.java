package com.example.CashBuzz.repository;

import com.example.CashBuzz.entity.Income;
import com.example.CashBuzz.enums.IncomeSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    // Filter by source
    List<Income> findBySource(IncomeSource source);

    // Filter by date
    List<Income> findByIncomeDate(LocalDate incomeDate);

    // Filter by amount greater than
    List<Income> findByAmountGreaterThan(double amount);

    List<Income> findAllByOrderByAmountDesc();
}