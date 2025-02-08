package com.example.budgetsmart.presentation.ui.budgets.editBudget

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.budgetsmart.R
import com.example.budgetsmart.databinding.FragmentEditBudgetBinding
import com.example.budgetsmart.domain.model.Budget
import com.example.budgetsmart.domain.model.BudgetCategory
import com.example.budgetsmart.domain.model.enums.BudgetCategoryType
import com.example.budgetsmart.presentation.ui.budgets.BudgetViewModel
import com.google.android.material.button.MaterialButton
import java.util.Locale

class EditBudgetFragment : Fragment() {

    private var _binding: FragmentEditBudgetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BudgetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeViewModel()
        setupCategoryAmountListeners()
    }

    private fun observeViewModel() {
        viewModel.currentBudget.observe(viewLifecycleOwner) { budget ->
            updateBudgetUI(budget)
            setEditText(budget.categories)
        }
    }

    private fun updateBudgetUI(budget: Budget) {
        binding.tvTotalBudgetAmount.text = "$${String.format("%.2f", budget.totalAmount)}"
        binding.tvRemainingBudget.text = "Remaining: $${String.format("%.2f", viewModel.getRemainingBudget())}"
    }

    private fun setEditText(categories: List<BudgetCategory>) {
        removeTextWatchers()

        binding.apply {
            categories.forEach { category ->
                when (category.categoryName) {
                    BudgetCategoryType.HOUSING.displayName -> etHousingAmount.setText(String.format(Locale.US, "%.2f", category.allocatedAmount))
                    BudgetCategoryType.FOOD.displayName -> etFoodAmount.setText(String.format(Locale.US, "%.2f", category.allocatedAmount))
                    BudgetCategoryType.TRANSPORTATION.displayName -> etTransportationAmount.setText(String.format(Locale.US, "%.2f", category.allocatedAmount))
                    BudgetCategoryType.ENTERTAINMENT.displayName -> etEntertainmentAmount.setText(String.format(Locale.US, "%.2f", category.allocatedAmount))
                    BudgetCategoryType.UTILITIES.displayName -> etUtilitiesAmount.setText(String.format(Locale.US, "%.2f", category.allocatedAmount))
                    BudgetCategoryType.SHOPPING.displayName -> etShoppingAmount.setText(String.format(Locale.US, "%.2f", category.allocatedAmount))
                    BudgetCategoryType.OTHER.displayName -> etOtherAmount.setText(String.format(Locale.US, "%.2f", category.allocatedAmount))
                }
            }
        }

        setupCategoryAmountListeners()
    }

    private fun setupClickListeners() {
        binding.btnEditTotal.setOnClickListener {
            showEditBudgetDialog()
        }

        binding.btnSaveBudget.setOnClickListener {
            // Add logging for each category
            val housingAmount = binding.etHousingAmount.text.toString().toDoubleOrNull() ?: 0.0
            val foodAmount = binding.etFoodAmount.text.toString().toDoubleOrNull() ?: 0.0
            val transportAmount = binding.etTransportationAmount.text.toString().toDoubleOrNull() ?: 0.0
            val entertainmentAmount = binding.etEntertainmentAmount.text.toString().toDoubleOrNull() ?: 0.0
            val utilitiesAmount = binding.etUtilitiesAmount.text.toString().toDoubleOrNull() ?: 0.0
            val shoppingAmount = binding.etShoppingAmount.text.toString().toDoubleOrNull() ?: 0.0
            val otherAmount = binding.etOtherAmount.text.toString().toDoubleOrNull() ?: 0.0

            Log.d("EditBudgetFragment", """
                Saving amounts:
                Housing: $housingAmount
                Food: $foodAmount
                Transport: $transportAmount
                Entertainment: $entertainmentAmount
                Utilities: $utilitiesAmount
                Shopping: $shoppingAmount
                Other: $otherAmount
            """.trimIndent())

            viewModel.updateCategoryBudget(BudgetCategoryType.HOUSING.displayName, housingAmount)
            viewModel.updateCategoryBudget(BudgetCategoryType.FOOD.displayName, foodAmount)
            viewModel.updateCategoryBudget(BudgetCategoryType.TRANSPORTATION.displayName, transportAmount)
            viewModel.updateCategoryBudget(BudgetCategoryType.ENTERTAINMENT.displayName, entertainmentAmount)
            viewModel.updateCategoryBudget(BudgetCategoryType.UTILITIES.displayName, utilitiesAmount)
            viewModel.updateCategoryBudget(BudgetCategoryType.SHOPPING.displayName, shoppingAmount)
            viewModel.updateCategoryBudget(BudgetCategoryType.OTHER.displayName, otherAmount)

            // Add logging after updates
            Log.d("EditBudgetFragment", "Current budget after save: ${viewModel.currentBudget.value?.categories}")

            // close the fragment
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setupCategoryAmountListeners() {
        binding.apply {
            val watcher = createCategoryTextWatcher()

            // Store watchers to remove them later
            val editTexts = listOf(
                etHousingAmount,
                etFoodAmount,
                etTransportationAmount,
                etEntertainmentAmount,
                etUtilitiesAmount,
                etShoppingAmount,
                etOtherAmount
            )

            editTexts.forEach { it.addTextChangedListener(watcher) }
        }

    }

    private fun removeTextWatchers() {
        binding.apply {
            listOf(
                etHousingAmount,
                etFoodAmount,
                etTransportationAmount,
                etEntertainmentAmount,
                etUtilitiesAmount,
                etShoppingAmount,
                etOtherAmount
            ).forEach { it.removeTextChangedListener(createCategoryTextWatcher()) }
        }
    }

    private fun createCategoryTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            validateTotalCategoryAmounts()
        }
    }

    private fun validateTotalCategoryAmounts() {
        val totalBudget = viewModel.currentBudget.value?.totalAmount ?: 0.0
        val currentTotal = calculateCurrentCategoryTotal()

        if (currentTotal > totalBudget) {
            binding.btnSaveBudget.isEnabled = false
            showError("Category allocations exceed total budget")
        } else {
            binding.btnSaveBudget.isEnabled = true
            clearError()
        }
    }

    private fun calculateCurrentCategoryTotal(): Double {
        return binding.run {
            listOf(
                etHousingAmount.text.toString(),
                etFoodAmount.text.toString(),
                etTransportationAmount.text.toString(),
                etEntertainmentAmount.text.toString(),
                etUtilitiesAmount.text.toString(),
                etShoppingAmount.text.toString(),
                etOtherAmount.text.toString()
            ).sumOf { it.toDoubleOrNull() ?: 0.0 }
        }
    }

    private fun showError(message: String) {
        binding.tvRemainingBudget.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        binding.tvRemainingBudget.text = message
    }

    private fun clearError() {
        binding.tvRemainingBudget.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkBlue))
        val totalBudget = viewModel.currentBudget.value?.totalAmount ?: 0.0
        val currentTotal = calculateCurrentCategoryTotal()
        binding.tvRemainingBudget.text = "Remaining: $${String.format("%.2f", totalBudget - currentTotal)}"
    }

    private fun showEditBudgetDialog() {
        // Inflate the custom dialog layout
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_budget, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Initialize dialog UI components
        val etBudgetAmount: EditText = dialogView.findViewById(R.id.etBudgetAmount)
        val btnSave: MaterialButton = dialogView.findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val newBudgetAmount = etBudgetAmount.text.toString()
            if (newBudgetAmount.isNotEmpty()) {
                viewModel.updateTotalBudget(newBudgetAmount.toDouble())
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}