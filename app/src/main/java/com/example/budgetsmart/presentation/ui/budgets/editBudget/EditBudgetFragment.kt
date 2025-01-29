package com.example.budgetsmart.presentation.ui.budgets.editBudget

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetsmart.R

class EditBudgetFragment : Fragment() {

    companion object {
        fun newInstance() = EditBudgetFragment()
    }

    private val viewModel: EditBudgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_edit_budget, container, false)
    }
}