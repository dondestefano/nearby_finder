package com.example.nearby_finder.util

import java.security.SecureRandom
import java.util.HashMap
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class Encryption {

    fun encrypt(dataToEncrypt: ByteArray, password: CharArray): HashMap<String, ByteArray> {

        val map = HashMap<String, ByteArray>()

        // Generate a random salt
        val random = SecureRandom()
        val salt = ByteArray(256)
        random.nextBytes(salt)

        // Use pbKeySpec to generate a key with a length of 256 bytes (standard).
        val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        // Generate key with AES (Well known recommended standard).
        val keySpec = SecretKeySpec(keyBytes, "AES")


        // Use IV instead of CBC to prevent encryption blocks from being similar.
        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(dataToEncrypt)

        // Add the necessary components into a HashMap.
        map["salt"] = salt
        map["iv"] = iv
        map["encrypted"] = encrypted

        return map
    }

}