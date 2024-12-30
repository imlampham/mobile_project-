package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.net.InetAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.UnknownHostException

suspend fun fetchGoogleSheetData(apiUrl: String): String? {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(apiUrl).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) response.body?.string() else null
    }
}
suspend fun isOnline(): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val ipAddr = InetAddress.getByName("google.com")
            ipAddr != null
        } catch (e: UnknownHostException) {
            false
        } catch (e: IOException) {
            false
        }
    }
}

fun showAlertDialog(
    context: Context,
    message: String,
    positiveButtonText: String = "OK",
    onPositiveClick: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(context)
    builder.setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            dialog.dismiss() // Dismiss the dialog
            onPositiveClick?.invoke() // Execute the callback if provided
        }

    val alert = builder.create()
    alert.show()
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

