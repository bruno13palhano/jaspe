package com.bruno13palhano.model

data class Notification(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val isVisualized: Boolean = false,
    val type: String = ""
)
