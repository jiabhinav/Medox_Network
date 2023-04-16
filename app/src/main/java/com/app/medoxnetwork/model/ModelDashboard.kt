package com.app.medoxnetwork.model

data class ModelDashboard(
    val result: Result,
    val status: Int
) {
    data class Result(
        val countries: Int,
        val direct_reward: Int,
        val my_staking: Int,
        val my_team: Int,
        val scratch_card: Int,
        val staking_reward: Int,
        val team_reward: Int,
        val total_participants: Int,
        val total_reward: Int,
        val total_rewards: Int,
        val total_rewards_usd: Int,
        val total_staked_in_pool: Int,
        val total_staked_in_pool_usd: Int,
        val total_withdraw: Int,
        val total_withdraw_usd: Int
    )
}