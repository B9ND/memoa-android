package com.dlrjsgml.memoa.network.data.user.getUser

data class GetResponse(
    val department: Department,
    val description: String,
    val email: String,
    val followed: Boolean,
    val nickname: String,
    val profileImage: String
)
data class Department(
    val grade: Int,
    val name: String,
    val school: String,
    val subjects: List<String>
)