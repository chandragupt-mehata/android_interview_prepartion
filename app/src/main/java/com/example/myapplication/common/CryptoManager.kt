package com.example.myapplication.common

import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator

class CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM$BLOCK_MODE$PADDING"
    }

}

fun main() {
    val str = "new one this is what I see"
    val newKey = ByteArray(16)
    //val key = SecretKeySpec(newKey, "AES")
    val keyGenerator = KeyGenerator.getInstance("AES").apply {
        init(128, SecureRandom())
    }
    val key = keyGenerator.generateKey()
    val cipher = Cipher.getInstance("AES").apply {
        init(Cipher.ENCRYPT_MODE, key)
    }
    val msg = "Hi how are you"
    val cipherText = cipher.doFinal(msg.toByteArray())
    println("cipher text: $cipherText")

    val cipherDecrypt = Cipher.getInstance("AES").apply {
        init(Cipher.DECRYPT_MODE, key)
    }
    val originalMsg = cipherDecrypt.doFinal(cipherText)
    println("originalMsg text: ${String(originalMsg)}, >>>> : $originalMsg")
}