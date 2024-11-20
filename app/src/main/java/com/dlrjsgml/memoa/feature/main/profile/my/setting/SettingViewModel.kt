package com.dlrjsgml.memoa.feature.main.profile.my.setting

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.feature.main.profile.my.MyProfileEffect
import com.dlrjsgml.memoa.feature.main.write.UpLoadImageSideEffect
import com.dlrjsgml.memoa.network.data.user.saveRefToken
import com.dlrjsgml.memoa.network.write.image.FileUtil
import com.dlrjsgml.memoa.network.write.image.FormDataUtil
import com.dlrjsgml.memoa.network.write.image.UriUtil
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

data class ProfileImageSettingState(
    val profileImage: String = ""
)
sealed interface SettingSideEffect {
    data object LogoutSuccess : SettingSideEffect
    data object LogoutFailed : SettingSideEffect
}

class SettingViewModel : ViewModel() {
    private val _uiEffect = MutableSharedFlow<SettingSideEffect>()
    val uiEffect: SharedFlow<SettingSideEffect> = _uiEffect.asSharedFlow()

    fun logout(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveRefToken(context, null)
                _uiEffect.emit(SettingSideEffect.LogoutSuccess)
            } catch (e: Exception) {

                _uiEffect.emit(SettingSideEffect.LogoutFailed)
            }
        }
    }

}