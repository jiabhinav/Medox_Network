package com.app.medoxnetwork.model

data class ModelResetPassword(
    val result: Result,
    val status: Int
) {
    data class Result(
        val msg: String
    )
}