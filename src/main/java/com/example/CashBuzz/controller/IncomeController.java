package com.example.CashBuzz.controller;


import com.example.CashBuzz.entity.Income;
import com.example.CashBuzz.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    // ➕ ADD INCOME
    @PostMapping("/add")
    public Income addIncome(@RequestBody Income income) {
        return incomeRepository.save(income);
    }

    // 📄 GET ALL INCOME (testing)
    @GetMapping("/all")
    public List<Income> getAllIncome() {
        return incomeRepository.findAll();
    }
    @GetMapping("/sort/amount/desc")
    public List<Income> sortIncomeByAmountDesc() {
        return incomeRepository.findAllByOrderByAmountDesc();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Long id) {
        incomeRepository.deleteById(id);
        return "Income deleted";
    }

}