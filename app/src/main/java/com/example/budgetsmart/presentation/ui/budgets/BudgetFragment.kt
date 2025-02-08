package com.example.budgetsmart.presentation.ui.budgets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetsmart.R
import com.example.budgetsmart.databinding.FragmentBudgetBinding
import com.example.budgetsmart.domain.model.Budget
import com.example.budgetsmart.presentation.adapters.BudgetCategoryAdapter
import com.example.budgetsmart.presentation.ui.budgets.editBudget.EditBudgetFragment
import java.util.Locale


class BudgetFragment : Fragment() {
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BudgetViewModel by activityViewModels()

    private lateinit var budgetCategoryAdapter: BudgetCategoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        budgetCategoryAdapter = BudgetCategoryAdapter()
        binding.categoriesList.apply {
            adapter = budgetCategoryAdapter
            layoutManager = LinearLayoutManager(context)
        }
        Log.d("BudgetFragment", "Categories: ${viewModel.currentBudget.value?.categories}")
    }


    private fun observeViewModel() {
        viewModel.currentBudget.observe(viewLifecycleOwner) { budget ->
            updateBudgetUI(budget)
        }
    }

    private fun updateBudgetUI(budget: Budget) {
        Log.d("BudgetFragment", "updateBudgetUI called")
        binding.apply {
            budgetLBLTotalBudget.text = "Total Budget: $${String.format(Locale.US, "%.2f", budget.totalAmount)}"

            val spent = viewModel.getTotalSpent()
            val remaining = viewModel.getRemainingBudget()

            budgetLBLSpentRemaining.text =
                "Spent: $${String.format(Locale.US, "%.2f", spent)} | Remaining: $${String.format(Locale.US, "%.2f", remaining)}"

            Log.d("BudgetFragment", "About to update adapter with ${budget.categories.size} categories")
            budgetCategoryAdapter.updateCategories(budget.categories)
        }
    }

    private fun setupClickListeners() {
        binding.budgetBTNEditBudgetButton.setOnClickListener {
            val editBudgetFragment = EditBudgetFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, editBudgetFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}