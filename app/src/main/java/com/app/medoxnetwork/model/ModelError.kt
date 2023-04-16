package com.app.medoxnetwork.model

data class ModelError(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)