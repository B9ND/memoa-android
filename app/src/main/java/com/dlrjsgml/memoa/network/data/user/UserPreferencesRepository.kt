package com.dlrjsgml.memoa.network.data.user

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "token_data_store")