package com.example.budgetsmart.domain.repository

import com.example.budgetsmart.domain.model.Transaction
import com.example.budgetsmart.domain.model.enums.TransactionType

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction)
    suspend fun getTransactions(): List<Transaction>
    suspend fun getTransactionsByMonth(year: Int, month: Int): List<Transaction>
    suspend fun getTransactionsByType(type: TransactionType): List<Transaction>
    suspend fun deleteTransaction(id: String)
    suspend fun updateTransaction(transaction: Transaction)
}