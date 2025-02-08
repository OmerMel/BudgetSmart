package com.example.budgetsmart.domain.model

data class BudgetCategory(
    val categoryName: String,
    val allocatedAmount: Double,
    val spentAmount: Double = 0.0,
) {
    // Helper property to calculate remaining amount
    val remainingAmount: Double
        get() = allocatedAmount - spentAmount

    // Helper property to calculate spending percentage
    val spendingPercentage: Double
        get() = if (allocatedAmount > 0) (spentAmount / allocatedAmount) * 100 else 0.0
}