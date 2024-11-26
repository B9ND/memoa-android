package com.dlrjsgml.memoa.network.data.user.getUser

import android.content.Context
import com.dlrjsgml.memoa.network.data.user.saveUser.ACC_TOKEN
import com.dlrjsgml.memoa.network.data.user.saveUser.DEPARTMENT_GRADE
import com.dlrjsgml.memoa.network.data.user.saveUser.DEPARTMENT_NAME
import com.dlrjsgml.memoa.network.data.user.saveUser.DEPARTMENT_SCHOOL
import com.dlrjsgml.memoa.network.data.user.saveUser.DEPARTMENT_SUBJECTS
import com.dlrjsgml.memoa.network.data.user.saveUser.DESCRIPTION
import com.dlrjsgml.memoa.network.data.user.Department
import com.dlrjsgml.memoa.network.data.user.saveUser.EMAIL
import com.dlrjsgml.memoa.network.data.user.saveUser.FOLLOWED
import com.dlrjsgml.memoa.network.data.user.saveUser.NICKNAME
import com.dlrjsgml.memoa.network.data.user.saveUser.PROFILE_IMAGE
import com.dlrjsgml.memoa.network.data.user.saveUser.REF_TOKEN
import com.dlrjsgml.memoa.network.data.user.UserProfile
import com.dlrjsgml.memoa.network.data.user.dataStore
import com.dlrjsgml.memoa.network.data.user.userProfileDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun getRefToken(context: Context): String? {
    return runBlocking {
        val preferences = context.dataStore.data.first()
        preferences[REF_TOKEN]
    }
}
fun getAccToken(context: Context): String? {
    return runBlocking {
        val preferences = context.dataStore.data.first()
        preferences[ACC_TOKEN]
    }
}
fun getUserProfile(context: Context): UserProfile = runBlocking {
    val preferences = context.userProfileDataStore.data.first()
    val department = Department(
        name = preferences[DEPARTMENT_NAME] ?: "",
        grade = preferences[DEPARTMENT_GRADE] ?: 0,
        school = preferences[DEPARTMENT_SCHOOL] ?: "",
        subjects = preferences[DEPARTMENT_SUBJECTS]?.split(",") ?: emptyList()
    )

    UserProfile(
        email = preferences[EMAIL] ?: "",
        nickname = preferences[NICKNAME] ?: "",
        description = preferences[DESCRIPTION] ?: "",
        profileImage = preferences[PROFILE_IMAGE] ?: "",
        department = department,
        followed = preferences[FOLLOWED] ?: false
    )
}

