package com.example.budgetsmart.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetsmart.data.repository.TransactionRepositoryImpl
import com.example.budgetsmart.domain.model.Transaction
import com.example.budgetsmart.domain.model.enums.Category
import com.example.budgetsmart.domain.model.enums.TransactionType
import com.example.budgetsmart.domain.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class   HomeViewModel(
    private val repository: TransactionRepository = TransactionRepositoryImpl.getInstance()
) : ViewModel() {
    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> = _balance

    private val _recentTransactions = MutableLiveData<List<Transaction>>()
    val recentTransactions: LiveData<List<Transaction>> = _recentTransactions

    private val _monthlyIncome = MutableLiveData<Double>()
    val monthlyIncome: LiveData<Double> = _monthlyIncome

    private val _monthlyExpense = MutableLiveData<Double>()
    val monthlyExpense: LiveData<Double> = _monthlyExpense

    private val _saveTransactionSuccess = MutableLiveData<Boolean>()
    val saveTransactionSuccess: LiveData<Boolean> = _saveTransactionSuccess

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            try {
                updateBalance()
                updateRecentTransactions()
                updateMonthlyStats()
            } catch (e: Exception) {
                Log.e("HomeViewModel","Debug: Error loading data", e)
            }
        }
    }

    private suspend fun updateBalance() {
        val transactions = repository.getTransactions()
        val total = transactions.sumOf { transaction ->
            when (transaction.type) {
                TransactionType.INCOME -> transaction.amount
                TransactionType.EXPENSE -> -transaction.amount
            }
        }
        _balance.value = total
    }

    private suspend fun updateRecentTransactions() {
        val recent = repository.getTransactions()
            .sortedByDescending { it.date }
            .take(5)
        _recentTransactions.value = recent
    }

    private suspend fun updateMonthlyStats() {
        val currentYear = LocalDateTime.now().year
        val currentMonth = LocalDateTime.now().monthValue
        val monthlyTransactions = repository.getTransactionsByMonth(currentYear, currentMonth)

        _monthlyIncome.value = monthlyTransactions
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }

        _monthlyExpense.value = monthlyTransactions
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }
    }

    fun addTransaction(
        amount: Double,
        category: Category,
        description: String,
        attachmentUri: String? = null
    ) {
        viewModelScope.launch {
            try {
                val transaction = Transaction(
                    amount = amount,
                    type = category.type,
                    category = category,
                    description = description,
                    date = LocalDateTime.now(),
                    attachmentUri = attachmentUri
                )
                repository.addTransaction(transaction)
                _saveTransactionSuccess.value = true
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error adding transaction", e)
                _saveTransactionSuccess.value = false
            }
        }
    }

    fun resetSaveState() {
        _saveTransactionSuccess.value = false
    }

}