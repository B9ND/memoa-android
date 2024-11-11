package com.dlrjsgml.memoa.network.data.signup

data class SignUpResponse (
    val email: String,
    val nickname: String,
    val description: String?,
    val profileImage: String,
    val departments: List<Department>
)

data class Department(
    val name: String,
    val grade: Int,
    val school: String,
    val subjects: List<String>
)