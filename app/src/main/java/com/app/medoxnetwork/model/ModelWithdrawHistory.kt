package com.app.medoxnetwork.model

data class ModelWithdrawHistory(
    val result: List<Result?>?,
    val status: Int?
) {
    data class Result(
        val amount: String?,
        val date: String?,
        val remarks: String?
    )
}