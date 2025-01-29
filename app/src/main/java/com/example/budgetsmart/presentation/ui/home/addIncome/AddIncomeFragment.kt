package com.example.budgetsmart.presentation.ui.home.addIncome

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.budgetsmart.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddIncomeFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = AddIncomeFragment()
    }

    private val viewModel: AddIncomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_income, container, false)
    }
}