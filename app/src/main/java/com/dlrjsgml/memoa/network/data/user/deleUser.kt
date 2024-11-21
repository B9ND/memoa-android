package com.dlrjsgml.memoa.network.data.user

import android.content.Context
import android.util.Log
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import com.dlrjsgml.memoa.MemoaApplication

suspend fun clearToken(context: Context) {
    context.dataStore.edit { preferences ->
        preferences.remove(ACC_TOKEN)
        preferences.remove(REF_TOKEN)
    }
    Log.d("Token", "토큰이 삭제되었습니다.")
    Log.d("Token", "토큰이 삭제되었습니다.${getAccToken(MemoaApplication.getContext())}")
    Log.d("Token", "토큰이 삭제되었습니다.${getRefToken(MemoaApplication.getContext())}")
}
