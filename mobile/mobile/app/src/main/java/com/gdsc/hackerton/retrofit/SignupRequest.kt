package com.gdsc.hackerton.retrofit

data class SignupRequest(
    val email:String,
    val password:String,
    val name:String,
    val bank:String,
    val accountNumber:String
)

