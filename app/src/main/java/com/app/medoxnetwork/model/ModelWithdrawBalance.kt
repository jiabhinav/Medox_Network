package com.app.medoxnetwork.model

data class ModelWithdrawBalance(
    val result: Result?,
    val status: Int?
) {
    data class Result(
        val balance: Double?
    )
}