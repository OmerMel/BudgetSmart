package com.example.budgetsmart.domain.model

import com.example.budgetsmart.domain.model.enums.Category
import com.example.budgetsmart.domain.model.enums.TransactionType
import java.time.LocalDateTime
import java.util.UUID

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val amount: Double,
    val type: TransactionType,
    val category: Category,
    val description: String,
    val date: LocalDateTime,
    //val currency: Currency,
    val attachmentUri: String? = null,
)