package com.dlrjsgml.memoa.network.data.user.saveUser

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dlrjsgml.memoa.network.data.user.dataStore
import com.dlrjsgml.memoa.network.data.user.getUser.GetResponse
import com.dlrjsgml.memoa.network.data.user.userProfileDataStore
import kotlinx.coroutines.runBlocking

val REF_TOKEN = stringPreferencesKey("ref_token")
val ACC_TOKEN = stringPreferencesKey("acc_token")
val EMAIL = stringPreferencesKey("email")
val NICKNAME = stringPreferencesKey("nickname")
val DESCRIPTION = stringPreferencesKey("description")
val PROFILE_IMAGE = stringPreferencesKey("profile_image")
val DEPARTMENT_NAME = stringPreferencesKey("department_name")
val DEPARTMENT_GRADE = intPreferencesKey("department_grade")
val DEPARTMENT_SCHOOL = stringPreferencesKey("department_school")
val DEPARTMENT_SUBJECTS = stringPreferencesKey("department_subjects")
val FOLLOWED = booleanPreferencesKey("followed")

fun saveRefToken(context: Context, token: String?) {
    runBlocking {
        try {
            context.dataStore.edit { preferences ->
                if (token != null) {
                    preferences[REF_TOKEN] = token
                }
                else {
                    preferences.remove(REF_TOKEN)
                }
            }
        } catch (e: Exception) {
            Log.d("토큰오류", "$e")
        }
    }
}
fun saveAccToken(context: Context, token: String?) {
    runBlocking {
        try {
            context.dataStore.edit { preferences ->
                if (token != null) {
                    preferences[ACC_TOKEN] = token
                }
                else {
                    preferences.remove(ACC_TOKEN)
                }
            }
        } catch (e: Exception) {
            Log.d("토큰오류", "saveAccToken: $e")
        }
    }
}
fun saveUserProfile(context: Context, userProfile: GetResponse) {
    runBlocking {
        context.userProfileDataStore.edit { preferences ->
            preferences[EMAIL] = userProfile.email
            preferences[NICKNAME] = userProfile.nickname
            preferences[DESCRIPTION] = userProfile.description
            preferences[PROFILE_IMAGE] = userProfile.profileImage
            preferences[DEPARTMENT_NAME] = userProfile.department.name
            preferences[DEPARTMENT_GRADE] = userProfile.department.grade
            preferences[DEPARTMENT_SCHOOL] = userProfile.department.school
            preferences[DEPARTMENT_SUBJECTS] = userProfile.department.subjects.joinToString(",") // 리스트를 문자열로 변환
            preferences[FOLLOWED] = userProfile.followed
        }
    }
}


