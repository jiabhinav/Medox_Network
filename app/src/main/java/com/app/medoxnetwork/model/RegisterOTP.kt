package com.app.medoxnetwork.model

data class RegisterOTP(
    val result: Result,
    val status: Int
) {
    data class Result(
        val msg: String,
        val otp: Int,
    )
}