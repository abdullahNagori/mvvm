package com.example.abl.model

data class ChangePasswordModel(
    val login_id: String,
    val new_password: String,
    val new_password_confirmation: String,
    val old_password: String
)