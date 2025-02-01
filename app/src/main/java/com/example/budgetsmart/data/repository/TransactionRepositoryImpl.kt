package com.example.budgetsmart.data.repository

import com.example.budgetsmart.domain.model.Transaction
import com.example.budgetsmart.domain.model.enums.TransactionType
import com.example.budgetsmart.domain.repository.TransactionRepository

class TransactionRepositoryImpl private constructor(): TransactionRepository {

    // For now, we'll use in-memory storage. Later we can switch to Room
    private val transactions = mutableListOf<Transaction>()

    companion object {
        @Volatile
        private var instance: TransactionRepositoryImpl? = null

        fun getInstance(): TransactionRepositoryImpl {
            return instance ?: synchronized(this) {
                instance ?: TransactionRepositoryImpl().also { instance = it }
            }
        }
    }



    override suspend fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    override suspend fun getTransactions(): List<Transaction> {
        return transactions.toList()
    }

    override suspend fun getTransactionsByMonth(year: Int, month: Int): List<Transaction> {
        return transactions.filter { transaction ->
            transaction.date.year == year && transaction.date.monthValue == month
        }
    }

    override suspend fun getTransactionsByType(type: TransactionType): List<Transaction> {
        return transactions.filter { it.type == type }
    }

    override suspend fun deleteTransaction(id: String) {
        transactions.removeIf { it.id == id }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        val index = transactions.indexOfFirst { it.id == transaction.id }
        if (index != -1) {
            transactions[index] = transaction
        }
    }

}