package com.example.budgetsmart.domain.model

import java.util.UUID

data class Account(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val balance: Double,
    //val currency: Currency,
)