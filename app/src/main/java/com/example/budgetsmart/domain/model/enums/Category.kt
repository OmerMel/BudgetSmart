package com.example.budgetsmart.domain.model.enums

enum class Category(
    val displayName: String,     // Human-readable name
    val type: TransactionType    // INCOME or EXPENSE
) {
    // Income Categories
    SALARY("Salary", TransactionType.INCOME),
    BUSINESS("Business", TransactionType.INCOME),
    INVESTMENTS("Investments", TransactionType.INCOME),
    RENTAL("Rental Income", TransactionType.INCOME),
    GIFTS("Gifts", TransactionType.INCOME),
    OTHER_INCOME("Other Income", TransactionType.INCOME),

    // Expense Categories
    HOUSING("Housing", TransactionType.EXPENSE),
    TRANSPORTATION("Transportation", TransactionType.EXPENSE),
    FOOD("Food & Dining", TransactionType.EXPENSE),
    UTILITIES("Utilities", TransactionType.EXPENSE),
    HEALTHCARE("Healthcare", TransactionType.EXPENSE),
    SHOPPING("Shopping", TransactionType.EXPENSE),
    ENTERTAINMENT("Entertainment", TransactionType.EXPENSE),
    EDUCATION("Education", TransactionType.EXPENSE),
    INSURANCE("Insurance", TransactionType.EXPENSE),
    SAVINGS("Savings", TransactionType.EXPENSE),
    DEBT("Debt Payment", TransactionType.EXPENSE),
    OTHER_EXPENSE("Other Expense", TransactionType.EXPENSE);

    companion object {
        // Helper function to get all income categories
        fun getIncomeCategories(): List<Category> =
            entries.filter { it.type == TransactionType.INCOME }

        // Helper function to get all expense categories
        fun getExpenseCategories(): List<Category> =
            entries.filter { it.type == TransactionType.EXPENSE }
    }
}