package com.example.nearby_finder.managers

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.nearby_finder.R
import java.security.SecureRandom
import java.util.*

object SharedPrefHelper {
    lateinit var encryptionPassword: CharArray
    lateinit var sharedPref: SharedPreferences

    lateinit var key: String

    fun init(context: Context) {
        key = context.getString(R.string.password_key)
        sharedPref = context.getSharedPreferences(
                key , Context.MODE_PRIVATE)

        getPassword(context)
    }

    private fun saveToSharedPref(newPassword: String) {
        sharedPref.edit().putString(key, newPassword).apply()
    }

    fun getPassword(context: Context) {
        val default = context.getString(R.string.default_password)
        encryptionPassword = sharedPref.getString(key, default)?.toCharArray() ?: "hejhej".toCharArray()

        Toast.makeText(context, "Old Password: " + encryptionPassword.joinToString(),Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateNewPassword(context: Context) {
        val random = SecureRandom()
        val bytes = ByteArray(20)
        random.nextBytes(bytes)
        val encoder: Base64.Encoder = Base64.getUrlEncoder().withoutPadding()
        val newPassword: String = encoder.encodeToString(bytes)

        saveToSharedPref(newPassword)
        encryptionPassword = newPassword.toCharArray()

        Toast.makeText(context, "New Password: " + encryptionPassword.joinToString() ,Toast.LENGTH_SHORT).show()
    }
}