package com.example.budgetsmart.presentation.ui.home.addIncome

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetsmart.domain.model.enums.Category
import com.example.budgetsmart.domain.model.enums.TransactionType

class AddIncomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TransactionRepository.getInstance(application)

    private val _state = MutableLiveData<AddIncomeState>(AddIncomeState.Initial)
    val state: LiveData<AddIncomeState> = _state

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _selectedCategory = MutableLiveData<Category?>()
    val selectedCategory: LiveData<Category?> = _selectedCategory

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val incomeCategories = repository.getCategories(TransactionType.INCOME)
                _categories.value = incomeCategories
            } catch (e: Exception) {
                _state.value = AddIncomeState.Error("Failed to load categories: ${e.message}")
            }
        }
    }
}