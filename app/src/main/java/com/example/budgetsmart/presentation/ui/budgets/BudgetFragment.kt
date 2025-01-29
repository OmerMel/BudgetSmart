package com.example.budgetsmart.presentation.ui.budgets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.budgetsmart.R
import com.example.budgetsmart.presentation.ui.budgets.editBudget.EditBudgetFragment
import com.example.budgetsmart.presentation.ui.settings.profileSettings.ProfileSettingsFragment
import com.google.android.material.button.MaterialButton


class BudgetFragment : Fragment() {

    private lateinit var budget_BTN_editBudgetButton: MaterialButton
    private lateinit var budget_BTN_nextMonth: ImageButton
    private lateinit var budget_BTN_prevMonth: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews()
        initViews()
    }

    private fun findViews() {
        budget_BTN_editBudgetButton = requireView().findViewById(R.id.budget_BTN_editBudgetButton)
        budget_BTN_nextMonth = requireView().findViewById(R.id.budget_BTN_nextMonth)
        budget_BTN_prevMonth = requireView().findViewById(R.id.budget_BTN_prevMonth)
    }

    private fun initViews() {
        budget_BTN_editBudgetButton.setOnClickListener {
            val editBudgetFragment = EditBudgetFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, editBudgetFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}