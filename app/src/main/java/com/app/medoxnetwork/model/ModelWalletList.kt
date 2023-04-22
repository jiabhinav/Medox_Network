package com.app.medoxnetwork.model

data class ModelWalletList(
    val result: Result,
    val status: Int
) {
    data class Result(
        val bonus_wallet: Int,
        val funding_wallet: Int,
        val reward_wallet: Int
    )
}