package com.example.abl.model

data class LoginResponse(
    var token: String? = null,
    var two_factor: String? = null
)