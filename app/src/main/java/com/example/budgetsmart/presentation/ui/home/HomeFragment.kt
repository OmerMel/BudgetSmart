package com.example.budgetsmart.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.budgetsmart.R
import com.example.budgetsmart.presentation.ui.home.addExpense.AddExpenseFragment
import com.example.budgetsmart.presentation.ui.home.addIncome.AddIncomeFragment


class HomeFragment : Fragment() {

    private lateinit var home_LL_addExpense: LinearLayout
    private lateinit var home_LL_addIncome: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews()
        initViews()
    }

    private fun findViews() {
        home_LL_addExpense = requireView().findViewById(R.id.home_LL_addExpense)
        home_LL_addIncome = requireView().findViewById(R.id.home_LL_addIncome)
    }

    private fun initViews() {
        home_LL_addExpense.setOnClickListener {
            val bottomSheet = AddExpenseFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
        home_LL_addIncome.setOnClickListener {
            val bottomSheet = AddIncomeFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }
}