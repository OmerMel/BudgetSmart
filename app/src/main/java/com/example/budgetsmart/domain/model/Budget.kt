package com.example.budgetsmart.domain.model

data class Budget(
    val id: String,
    val totalAmount: Double,
    val categories: List<BudgetCategory>,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
