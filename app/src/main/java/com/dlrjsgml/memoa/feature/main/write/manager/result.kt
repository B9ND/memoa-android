package com.dlrjsgml.memoa.feature.main.write.manager

import android.graphics.Bitmap

sealed class UploadResult {
    data class Success(val imageUrl: String) : UploadResult()
    data class Error(val message: String) : UploadResult()
    data object Canceled : UploadResult()
}
// Helper extension function to resize bitmap while maintaining aspect ratio
private fun Bitmap.resizeToFit(maxWidth: Int, maxHeight: Int): Bitmap {
    return if (width > maxWidth || height > maxHeight) {
        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        val finalWidth = if (ratioBitmap > ratioMax) {
            maxWidth
        } else {
            (maxHeight * ratioBitmap).toInt()
        }

        val finalHeight = if (ratioBitmap > ratioMax) {
            (maxWidth / ratioBitmap).toInt()
        } else {
            maxHeight
        }

        Bitmap.createScaledBitmap(this, finalWidth, finalHeight, true)
    } else {
        this
    }
}

fun Bitmap.scaleBitmapToFit(maxWidth: Int, maxHeight: Int): Bitmap {
    val width = this.width
    val height = this.height

    val ratioBitmap = width.toFloat() / height.toFloat()
    val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

    var finalWidth = maxWidth
    var finalHeight = maxHeight

    if (ratioBitmap > ratioMax) {
        finalHeight = (maxWidth / ratioBitmap).toInt()
    } else {
        finalWidth = (maxHeight * ratioBitmap).toInt()
    }

    return Bitmap.createScaledBitmap(this, finalWidth, finalHeight, true)
}

class ImageCompressException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
