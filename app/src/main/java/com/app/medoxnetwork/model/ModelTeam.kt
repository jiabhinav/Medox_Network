package com.app.medoxnetwork.model

data class ModelTeam(
    val result: Result,
    val status: Int
) {
    data class Result(
        val Sponsor_name: String,
        val direct_downline: Int,
        val sponsor_email: String,
        val sponsor_phone: String,
        val sponsor_username: String,
        val total_downline: Int
    )
}