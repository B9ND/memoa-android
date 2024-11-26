package com.dlrjsgml.memoa.network.data.user

data class UserProfile(
    val email: String,
    val nickname: String,
    val description: String,
    val profileImage: String,
    val department: Department,
    val followed: Boolean
)

data class Department(
    val name: String,
    val grade: Int,
    val school: String,
    val subjects: List<String>
)
