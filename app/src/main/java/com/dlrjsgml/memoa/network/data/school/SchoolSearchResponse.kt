package com.dlrjsgml.memoa.network.data.school

data class SchoolSearchResponse (
    val name: String,
    val departments: List<Department>
)

data class Department(
    val id: Int,
    val name: String,
    val grade: Int,
    val subjects: List<String>
)
