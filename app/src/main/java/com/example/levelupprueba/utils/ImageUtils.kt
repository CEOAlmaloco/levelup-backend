package com.example.levelupprueba.utils

import android.content.Context
import android.net.Uri
import java.io.*
import java.lang.Exception

object ImageUtils {
    fun copyUriToInternalStorage(context: Context, uri: Uri, filename: String): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, filename)
            val outputStream: OutputStream = file.outputStream()
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception){
            null
        }
    }
}