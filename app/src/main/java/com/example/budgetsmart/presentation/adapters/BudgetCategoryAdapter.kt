package com.example.budgetsmart.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetsmart.databinding.ItemBudgetCategoryBinding
import com.example.budgetsmart.domain.model.BudgetCategory
import java.util.Locale

class BudgetCategoryAdapter : RecyclerView.Adapter<BudgetCategoryAdapter.BudgetCategoryViewHolder>() {

    private var categories: List<BudgetCategory> = emptyList()

    init {
        Log.d("BudgetAdapter", "Adapter initialized")
    }

    // ViewHolder class
    class BudgetCategoryViewHolder(
        private val binding: ItemBudgetCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: BudgetCategory) {
            binding.apply {

                root.setBackgroundColor(Color.WHITE)

                // Set category name
                categoryName.text = category.categoryName

                // Set amount status
                val amountText = "Spent: $${String.format(Locale.US, "%.2f", category.spentAmount)} " +
                        "of $${String.format(Locale.US, "%.2f", category.allocatedAmount)}"
                amountStatus.text = amountText

                // Set progress bar
                spendingProgress.progress = category.spendingPercentage.toInt()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetCategoryViewHolder {
        Log.d("BudgetAdapter", "onCreateViewHolder called")
        val binding = ItemBudgetCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BudgetCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetCategoryViewHolder, position: Int) {
        Log.d("BudgetAdapter", "onBindViewHolder called for position $position")
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun updateCategories(newCategories: List<BudgetCategory>) {
        Log.d("BudgetAdapter", "updateCategories called with ${newCategories.size} items")
        categories = newCategories
        notifyDataSetChanged()
    }
}