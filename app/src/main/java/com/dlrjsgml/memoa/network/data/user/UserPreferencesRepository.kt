package com.dlrjsgml.memoa.network.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "token")
val Context.userProfileDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_profile")