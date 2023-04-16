package com.app.medoxnetwork.model

data class ModelRegister(
    val result: Result,
    val status: Int
) {
    data class Result(
        val msg: String
    )
}