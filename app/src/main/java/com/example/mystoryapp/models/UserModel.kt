package com.example.mystoryapp.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("islogin")
    val isLogin: Boolean
)
