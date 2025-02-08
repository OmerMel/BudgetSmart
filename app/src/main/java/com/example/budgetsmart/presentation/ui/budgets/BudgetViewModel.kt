package com.example.budgetsmart.presentation.ui.budgets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.budgetsmart.domain.model.Budget
import com.example.budgetsmart.domain.model.BudgetCategory
import com.example.budgetsmart.domain.model.enums.BudgetCategoryType
import java.util.UUID

class BudgetViewModel : ViewModel() {

    // Current budget data
    private val _currentBudget = MutableLiveData<Budget>()
    val currentBudget: LiveData<Budget> = _currentBudget

    init {
        // Load initial budget data
        if (_currentBudget.value == null) {
            loadBudget()
        }
    }

    // Load budget data for selected month
    //TODO: change to load from data source
    private fun loadBudget() {

        val categories = BudgetCategoryType.entries.map { type ->
            BudgetCategory(
                categoryName = type.displayName,
                allocatedAmount = 0.0,
                spentAmount = 0.0
            )
        }

        _currentBudget.value = Budget(
            id = UUID.randomUUID().toString(),
            totalAmount = 0.0,
            categories = categories
        )
    }

    // Update total budget amount
    fun updateTotalBudget(amount: Double) {
        _currentBudget.value = _currentBudget.value?.copy(
            totalAmount = amount
        )
    }

    // Update category budget
    fun updateCategoryBudget(categoryName: String, amount: Double) {
        val currentBudget = _currentBudget.value ?: return

        val updatedCategories = currentBudget.categories.map { category ->
            if (category.categoryName == categoryName) {
                category.copy(allocatedAmount = amount)
            } else {
                category
            }
        }

        _currentBudget.value = _currentBudget.value?.copy(
            categories = updatedCategories
        )
    }

    // Calculate remaining budget
    fun getRemainingBudget(): Double {
        val total = _currentBudget.value?.totalAmount ?: 0.0
        val allocated = _currentBudget.value?.categories?.sumOf { it.allocatedAmount } ?: 0.0
        return total - allocated
    }

    // Get total spent amount
    fun getTotalSpent(): Double {
        return _currentBudget.value?.categories?.sumOf { it.spentAmount } ?: 0.0
    }

    fun getBudgetCategoryAmount(categoryName: String): Double {
        return _currentBudget.value?.categories?.find {
            it.categoryName == categoryName
        }?.allocatedAmount ?: 0.0
    }
}