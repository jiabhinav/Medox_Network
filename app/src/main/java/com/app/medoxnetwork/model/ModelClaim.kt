package com.app.medoxnetwork.model

data class ModelClaim(
    val result: Result,
    val status: Int
) {
    data class Result(
        val msg: String
    )
}