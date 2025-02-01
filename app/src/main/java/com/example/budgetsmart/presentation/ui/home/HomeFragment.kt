package com.example.budgetsmart.presentation.ui.home

import TransactionAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.NumberFormat
import com.example.budgetsmart.databinding.FragmentHomeBinding
import com.example.budgetsmart.domain.model.enums.TransactionType
import com.example.budgetsmart.presentation.ui.home.addExpense.AddExpenseFragment
import com.example.budgetsmart.presentation.ui.home.addIncome.AddIncomeFragment
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val transactionAdapter = TransactionAdapter()

    private val viewModel: HomeViewModel by viewModels()
    private val addViewModel: AddViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()

        addViewModel.saveTransactionSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                viewModel.loadData()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.transactionsRecyclerView.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupObservers() {
        viewModel.balance.observe(viewLifecycleOwner) { balance ->
            val formatter = NumberFormat.getCurrencyInstance(Locale.US)
            binding.homeLBLBalance.text = formatter.format(balance)
        }

        viewModel.monthlyIncome.observe(viewLifecycleOwner) { income ->
            binding.incomeProgress.progress = calculateProgressPercentage(income)
        }

        viewModel.monthlyExpense.observe(viewLifecycleOwner) { expense ->
            binding.expenseProgress.progress = calculateProgressPercentage(expense)
        }

        viewModel.recentTransactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.updateTransactions(transactions)
        }
    }

    private fun calculateProgressPercentage(amount: Double): Int {
        // Implement your logic to calculate progress percentage
        // This could be based on monthly budget or maximum monthly transaction
        return (amount / 1000.0 * 100).toInt().coerceIn(0, 100)
    }

    private fun setupClickListeners() {
        binding.homeLLAddExpense.setOnClickListener{
            showAddTransactionDialog(TransactionType.EXPENSE)
        }

        binding.homeLLAddIncome.setOnClickListener {
            showAddTransactionDialog(TransactionType.INCOME)
        }
    }

    private fun showAddTransactionDialog(type: TransactionType) {
        val dialog = when (type) {
            TransactionType.EXPENSE -> AddExpenseFragment()
            TransactionType.INCOME -> AddIncomeFragment()
        }
        dialog.show(childFragmentManager, "addTransaction")
    }

    fun refreshData() {
        viewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}