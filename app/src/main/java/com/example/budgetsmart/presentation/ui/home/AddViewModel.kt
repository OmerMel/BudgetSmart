package com.example.budgetsmart.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetsmart.data.repository.TransactionRepositoryImpl
import com.example.budgetsmart.domain.model.Transaction
import com.example.budgetsmart.domain.model.enums.Category
import com.example.budgetsmart.domain.repository.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddViewModel(
    private val repository: TransactionRepository = TransactionRepositoryImpl.getInstance()
): ViewModel() {
    private val _saveTransactionSuccess = MutableLiveData<Boolean>()
    val saveTransactionSuccess: LiveData<Boolean> = _saveTransactionSuccess

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
                println("Debug: Adding transaction in AddViewModel: $transaction")
                repository.addTransaction(transaction)
                _saveTransactionSuccess.value = true
            } catch (e: Exception) {
                println("Debug: Error adding transaction: ${e.message}")
                _saveTransactionSuccess.value = false
            }
        }
    }

    fun resetSaveState() {
        _saveTransactionSuccess.value = false
    }
}