package com.example.myapplication

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import parseSheetData
import java.net.InetAddress

data class SheetEntry(
    val column1: String,
    val column2: String
)

class SplashActivity : AppCompatActivity() {
    private val dbHelper = DatabaseHelper(this)

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            try {
                val hasInternet = isOnline()
                if (hasInternet) {
                    val apiUrl = "https://sheet-backend.tqtt-selfhost.workers.dev/"
                    val response = fetchGoogleSheetData(apiUrl)
                    if (response != null) {
                        val data = parseSheetData(response)
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        val gson = Gson()
                        val dataJson = gson.toJson(data)
                        intent.putExtra("data", dataJson)
                        // pushing to SQLite DB
                        withContext(Dispatchers.IO) {
                            dbHelper.clearTable()
                            data.forEach {
                                dbHelper.insertData(it.column1, it.column2)
                            }
                            dbHelper.forceCheckpoint()
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        showAlertDialog(
                            this@SplashActivity,
                            "Failed to fetch data. Proceeding offline."
                        ) {
                            startMainActivityWithoutData(dbHelper)
                        }
                    }
                } else {
                    showAlertDialog(
                        this@SplashActivity,
                        "No internet. Proceeding offline."
                    ) {
                        startMainActivityWithoutData(dbHelper)
                    }
                }
            } catch (e: Exception) {
                Log.e("SplashActivity", "Error: ${e.message}")
                Toast.makeText(this@SplashActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                startMainActivityWithoutData(dbHelper)
            }
        }
    }

    private fun startMainActivityWithoutData(dbHelper: DatabaseHelper) {
        val data = dbHelper.getAllData().map { (column1, column2) ->
            SheetEntry(column1, column2)
        }

        val gson = Gson()
        val dataJson = gson.toJson(data)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("data", dataJson)
        startActivity(intent)
        finish()
    }
}
