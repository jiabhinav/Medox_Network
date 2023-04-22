package com.app.medoxnetwork.model

data class ModelWalletHistory(
    val result: List<Result>,
    val status: Int
) {
    data class Result(
        val amount: Double,
        val balance: Double,
        val date: String,
        val remarks: String,
        val type: Int
    )
}