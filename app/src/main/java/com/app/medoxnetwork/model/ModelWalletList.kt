package com.app.medoxnetwork.model

data class ModelWalletList(
    val result: Result,
    val status: Int
) {
    data class Result(
        val bonus_wallet: Double,
        val funding_wallet: Double,
        val reward_wallet: Double
    )
}