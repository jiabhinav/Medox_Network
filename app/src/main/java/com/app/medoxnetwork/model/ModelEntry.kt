package com.app.medoxnetwork.model

data class ModelEntry(
    val count: Int,
    val entries: List<Entry>
) {
    data class Entry(
        val API: String,
        val Auth: String,
        val Category: String,
        val Cors: String,
        val Description: String,
        val HTTPS: Boolean,
        val Link: String
    )
}