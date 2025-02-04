package com.example.budgetsmart.presentation.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetsmart.data.repository.TransactionRepositoryImpl
import com.example.budgetsmart.domain.repository.TransactionRepository

class TransactionsViewModel(
    private val repository: TransactionRepository = TransactionRepositoryImpl.getInstance()
) : ViewModel(){
    private val _currentMonth = MutableLiveData<Double>()
    val balance: LiveData<Double> = _currentMonth

    private val _summaryText = MutableLiveData<String>()
    val summaryText: LiveData<String> = _summaryText

    private val _monthlyIncome = MutableLiveData<Double>()
    val monthlyIncome: LiveData<Double> = _monthlyIncome

    private val _monthlyExpense = MutableLiveData<Double>()
    val monthlyExpense: LiveData<Double> = _monthlyExpense

    init {
        loadData()
    }

    private fun loadData() {
        TODO("Not yet implemented")
    }

}