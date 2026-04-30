package com.example.expense_tracker.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendExpenseNotification(String toEmail, String category, Double amount, String description) {
        System.out.println("Expense added: " + category + " - ₹" + amount);
    }
}
