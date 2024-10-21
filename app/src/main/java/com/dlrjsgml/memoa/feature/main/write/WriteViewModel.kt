package com.dlrjsgml.memoa.feature.main.write

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.network.write.WriteDTO
import com.dlrjsgml.memoa.network.write.image.FileUtil
import com.dlrjsgml.memoa.network.write.image.FormDataUtil
import com.dlrjsgml.memoa.network.write.image.UriUtil
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.remote.TemporaryToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

data class WriteState(
    val title: String = "",
    val content: String = "",
    val tags: List<String> = emptyList(),
    val image: List<String> = emptyList(),
    val isReleased : Boolean = true
)

data class CustomAlertDialogState(
    val content: String = "",
    val onClickConfirm: () -> Unit = {},
)

sealed interface WriteSideEffect {
    data object Success : WriteSideEffect
    data object Failure : WriteSideEffect
}

sealed interface UpLoadImageSideEffect{
    data object CompressionFailure : WriteSideEffect
    data object Success : WriteSideEffect
    data object Failure : WriteSideEffect
}
class WriteViewModel : ViewModel() {


    private val _uiEffect = MutableSharedFlow<WriteSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(WriteState())
    val uiState = _uiState.asStateFlow()

    val customAlertDialogState: MutableState<CustomAlertDialogState> =
        mutableStateOf<CustomAlertDialogState>(
            CustomAlertDialogState()
        )


    fun wrigingErrorAlert(text : String) {
        customAlertDialogState.value = CustomAlertDialogState(
            content = text,
            onClickConfirm = {
                resetDialogState()
            },
        )
    }

    fun plsAllWrite() {
        customAlertDialogState.value = CustomAlertDialogState(
            content = "제목과 내용을 다 적어 주세요",
            onClickConfirm = {
                resetDialogState()
            },
        )
    }

    // 다이얼로그 상태 초기화
    fun resetDialogState() {
        customAlertDialogState.value = CustomAlertDialogState()
    }

    fun fillTags(tag: String) {
        _uiState.update {
            if (tag in it.tags) {
                it.copy(tags = it.tags - arrayListOf(tag))
            } else {
                it.copy(tags = it.tags + arrayListOf(tag))
            }
        }
        Log.d("ㅎㅇ", "${uiState.value.tags.sorted()}");
    }

    private fun convertResizeImage(context: Context, imageUri: Uri): Uri? {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)

        // 임시 파일을 만듦
        val tempFile = File.createTempFile("resized_image", ".jpg", context.cacheDir)
        val fileOutputStream = FileOutputStream(tempFile)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.close()

        // 파일을 MediaStore에 저장
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, tempFile.name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val contentUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        contentUri?.let {
            context.contentResolver.openOutputStream(it)?.use { outputStream ->
                val inputStream = FileInputStream(tempFile)
                inputStream.copyTo(outputStream)
                inputStream.close()
            }
        }

        // 모든 스트림이 제대로 닫혔는지 확인한 후 임시 파일 삭제
        if (tempFile.exists()) {
            val deleted = tempFile.delete()
            if (!deleted) {
                tempFile.deleteOnExit() // 임시 파일이 삭제되지 않으면 나중에 삭제하도록 예약
            }
        }

        return contentUri
    }


    fun uploadImage(uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val smallImage =  convertResizeImage(context,uri)
                Log.d("글쓰기", "uri : $uri");
                Log.d("글쓰기", "작은거 : $smallImage");

                val imageFile = UriUtil.toFile(context, uri)
                val resizedFile = FileUtil.resizeImageFile(context, imageFile, 1080, 1080)
                Log.d("글쓰기", "1글쓰기 중 : $resizedFile")
                val multipartImage: MultipartBody.Part =
                    FormDataUtil.getImageMultipart("file", resizedFile)

                val response = RetrofitClient.upLoadImgService.uploadImage(
                    TemporaryToken.AccessToken,
                    multipartImage
                )
                Log.d("글쓰기", "Uploading file: ${multipartImage}")
                Log.d("글쓰기", "ㅇㅇㅇㅇㅇ: ${response.url}")
                _uiState.update { it.copy(image = it.image + response.url) }
                _uiEffect.emit(UpLoadImageSideEffect.Success)
//                if(response.isSuccessful){
//                    Log.d("글쓰기", "성공: ${response.body()}")
//                    _uiState.update { it.copy(image = it.image + response.body().toString()) }
//                    Log.d("글쓰기", "성공: ${uiState.value.image}")
//
//                }else{
//                    Log.d("글쓰기", "실패: ${response.body()}")
//                }

            } catch (e: Exception) {
                if(e.message == null){
                    _uiEffect.emit(UpLoadImageSideEffect.CompressionFailure)
                } else{
                    _uiEffect.emit(UpLoadImageSideEffect.Failure)

                }
                Log.d("글쓰기", "글쓰기 오류: ${e.message}")
            }
        }
    }


    fun postWrite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val writeData = WriteDTO(
                    title = uiState.value.title,
                    content = uiState.value.content,
                    tags = uiState.value.tags,
                    images = uiState.value.image
                )
                Log.d("글쓰기", "글 내용 ㄱㅡ$writeData");

                val write = RetrofitClient.writeService.postWrite(
                    TemporaryToken.AccessToken,
                    writeData
                )
                _uiEffect.emit(WriteSideEffect.Success)

            } catch (e: Exception) {
                Log.d("글쓰기", e.message.toString());

                _uiEffect.emit(WriteSideEffect.Failure)
            }
        }
    }

    fun changeRelease(isReleased : Boolean){
        _uiState.update { it.copy(isReleased = isReleased) }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }
}