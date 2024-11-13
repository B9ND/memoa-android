package com.dlrjsgml.memoa.network.data.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.runBlocking

val REF_TOKEN = stringPreferencesKey("ref_token")
val ACC_TOKEN = stringPreferencesKey("acc_token")

fun saveRefToken(context: Context, token: String?) {
    runBlocking {
        context.dataStore.edit { preferences ->
            if (token != null) {
                preferences[REF_TOKEN] = token
            }
            else {
                preferences.remove(REF_TOKEN)
            }
        }
    }
}
fun saveAccToken(context: Context, token: String?) {
    runBlocking {
        context.dataStore.edit { preferences ->
            if (token != null) {
                preferences[ACC_TOKEN] = token
            }
            else {
                preferences.remove(ACC_TOKEN)
            }
        }
    }
}
