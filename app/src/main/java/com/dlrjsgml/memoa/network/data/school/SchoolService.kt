package com.dlrjsgml.memoa.network.data.school

import com.dlrjsgml.memoa.network.data.login.LoginRequest
import com.dlrjsgml.memoa.network.data.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SchoolService {
    @GET("/school/search")
    suspend fun schoolSearch(@Query("search") search: String): List<SchoolSearchResponse>
}
