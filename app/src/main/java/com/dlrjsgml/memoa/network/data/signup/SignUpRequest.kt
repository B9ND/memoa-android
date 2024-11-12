package com.dlrjsgml.memoa.network.data.signup

data class SignUpRequest (
    val email: String,
    val nickname: String,
    val password: String,
    val departmentId: Int
)