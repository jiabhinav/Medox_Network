package com.app.medoxnetwork.model

data class ModelTotalTeam(
    val result: Result,
    val status: Int
) {
    data class Result(
        val `data`: List<Data>
    ) {
        data class Data(
            val level: Int,
            val player: List<Player>
        ) {
            data class Player(
                val name: String,
                val reference: String,
                val staking: Int,
                val username: String
            )
        }
    }
}