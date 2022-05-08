package com.example.mystoryapp.models

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("name")
    var name: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String
)

data class LoginRequest(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String
)