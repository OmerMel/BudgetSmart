package com.example.budgetsmart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.budgetsmart.R


class HomeFragment : Fragment() {

    private lateinit var home_LL_addExpense: LinearLayout

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
    }

    private fun initViews() {
        home_LL_addExpense.setOnClickListener {
            val bottomSheet = AddExpenseFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }
}