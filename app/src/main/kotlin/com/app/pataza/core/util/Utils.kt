package com.app.pataza.core.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object Utils {
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = Calendar.getInstance().timeInMillis.toString()
        var storageDir: File? = null
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let { storageDir = it }
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        )
    }

    fun isImage(context: Context, uri: Uri): Boolean {
        val mimeType = context.contentResolver.getType(uri) ?: return true
        return mimeType.startsWith("image/")
    }

    private fun copyUriToFile(context: Context, uri: Uri, outputFile: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(outputFile)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }

    fun createImageFile(dir: File, fileName: String? = null): File {
        if (fileName == null) {
            val timestamp = Calendar.getInstance().timeInMillis.toString()
            return File.createTempFile("IMG_$timestamp", ".jpg", dir)
        }

        return File(dir, fileName)
    }

    fun importPhoto(context: Context?, uri: Uri, photoFile: File): MultipartBody.Part? {
        context?.let {
            if (!isImage(it, uri)) {
                // not image
                return null
            }

            return try {
                return MultipartBody.Part.createFormData("photos[]", photoFile.name, RequestBody.create(MediaType.parse("image/*"), photoFile))
            } catch (e: IOException) {
                e.printStackTrace()
                // handle error
                null
            }
        }
        return null
    }

    fun makeFile(context: Context?, uri: Uri): File?{
        return try {
            context?.let {
                val photoFile = createImageFile(it.cacheDir)
                copyUriToFile(context, uri, photoFile)
                return photoFile
            }
            null
        }catch (e: IOException){
            e.printStackTrace()
            null
        }
    }
}