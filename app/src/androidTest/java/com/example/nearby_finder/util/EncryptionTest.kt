package com.example.nearby_finder.util

import android.util.Base64
import org.junit.Assert.*
import org.junit.Test

class EncryptionTest {
    private val encryption = Encryption
    @Test
    fun test_encryption() {
        val messageToEncrypt = "This is a secret message."
        val password = "thisIsThePassword".toCharArray()
        val encryptedMessage = encryption.encrypt(messageToEncrypt.toByteArray(), password)

        val encryptedString = Base64.encodeToString(encryptedMessage["encrypted"], Base64.NO_WRAP)

        assertNotEquals(messageToEncrypt, encryptedString)

        val decryptedMessage = encryption.decrypt(encryptedMessage, password)

        assertEquals(messageToEncrypt, String(decryptedMessage!!, Charsets.UTF_8))
    }
}