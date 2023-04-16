package com.app.medoxnetwork.model

data class ModelUser(
    val result: Result,
    val status: Int
) {
    data class Result(
        val email: String,
        val image: String,
        val msg: String,
        val name: String,
        val phone: String,
        val username: String
    )
}