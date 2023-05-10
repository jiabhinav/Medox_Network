package com.app.medoxnetwork.model

data class ModelSuccess(
    val result: Result,
    val status: Int
) {
    data class Result(
        val msg: String
    )
}