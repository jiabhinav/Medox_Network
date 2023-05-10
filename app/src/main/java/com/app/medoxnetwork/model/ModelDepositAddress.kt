package com.app.medoxnetwork.model

data class ModelDepositAddress(
    val result: Result,
    val status: Int
) {
    data class Result(
        val deposit_address: String
    )
}