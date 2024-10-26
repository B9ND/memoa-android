package com.dlrjsgml.memoa.network.write.image

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.P)
fun ContentResolver.uriToBitmap(uri: Uri): Bitmap = ImageDecoder.decodeBitmap(
    ImageDecoder.createSource(this, uri),
)

@SuppressLint("Range")
fun ContentResolver.getFileName(uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor: Cursor? = this.query(uri, null, null, null, null)
        cursor.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = uri.lastPathSegment
    }
    return result
}

object FileUtil {
    // 임시 파일 생성
    fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    // 이미지 회전
    private fun rotateImageIfRequired(bitmap: Bitmap, imgPath: String): Bitmap {
        val exif = ExifInterface(imgPath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> bitmap // 변환할 필요가 없는 경우
        }
    }

    // Bitmap 회전
    private fun rotateImage(source: Bitmap, angle: Int): Bitmap {
        val matrix = Matrix().apply {
            postRotate(angle.toFloat())
        }
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    // 리사이즈된 이미지를 파일에 저장
    fun resizeImageFile(context: Context, originalFile: File, newWidth: Int, newHeight: Int): File {
        // BitmapFactory를 사용해 원본 파일을 Bitmap으로 변환
        val bitmap = BitmapFactory.decodeFile(originalFile.absolutePath)

        // 이미지 회전 처리
        val rotatedBitmap = rotateImageIfRequired(bitmap, originalFile.absolutePath)

        // Bitmap 크기 조정
        val resizedBitmap = Bitmap.createScaledBitmap(rotatedBitmap, newWidth, newHeight, true)

        // 임시 파일 생성
        val resizedFile = createTempFile(context, "resized_" + originalFile.name)

        // 파일에 리사이즈된 이미지 저장
        val outputStream = FileOutputStream(resizedFile)
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream) // JPEG 포맷으로 90% 품질로 저장
        outputStream.flush()
        outputStream.close()

        return resizedFile
    }

    // 파일 내용 스트림 복사
    @SuppressLint("Recycle")
    fun copyToFile(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }
        outputStream.flush()
    }
}

object UriUtil {
    // URI -> File
    fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)

        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.copyToFile(context, uri, file)

        return File(file.absolutePath)
    }

    // get file name & extension
    fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)!!.split("/").last()

        return "$name.$ext"
    }
}
object FormDataUtil {
    // File -> Multipart
    fun getImageMultipart(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("image/*".toMediaType())
        )
    }
    // String -> RequestBody
    fun getTextRequestBody(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaType())
    }
}