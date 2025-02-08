package com.example.budgetsmart.presentation.ui.home.addExpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.budgetsmart.databinding.FragmentAddExpenseBinding
import com.example.budgetsmart.domain.model.enums.Category
import com.example.budgetsmart.presentation.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddExpenseFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    private var selectedReceiptUri: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryDropdown()
        setupSaveButton()
        setupReceiptAttachment()
        observeViewModel()
    }

    private fun setupCategoryDropdown() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            Category.getExpenseCategories().map { it.displayName }
        )
        binding.categoryDropdown.setAdapter(adapter)
    }

    private fun setupReceiptAttachment() {
        binding.attachmentCard.setOnClickListener {
            // TODO: Implement image picking functionality
            selectedReceiptUri = "dummy_receipt_uri"
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val amount = binding.amountInput.text.toString()
            val categoryName = binding.categoryDropdown.text.toString()
            val note = binding.noteInput.text.toString()

            if (!validateAmount(amount)) {
                binding.amountInput.error = "Please enter a valid amount"
                return@setOnClickListener
            }

            val selectedCategory = Category.getExpenseCategories()
                .find { it.displayName == categoryName }

            if (selectedCategory == null) {
                binding.categoryDropdown.error = "Please select a category"
                return@setOnClickListener
            }

            viewModel.addTransaction(
                amount = amount.toDouble(),
                category = selectedCategory,
                description = note,
                attachmentUri = selectedReceiptUri
            )
        }
    }

    private fun validateAmount(amount: String): Boolean {
        return try {
            amount.toDouble() > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun observeViewModel() {
        viewModel.saveTransactionSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}