package com.example.myapplication.test

/**
 * https://medium.com/androiddevelopers/data-encryption-on-android-with-jetpack-security-e4cb0b2d2a9
 * https://medium.com/@Naibeck/android-security-encryptedsharedpreferences-ea239e717e5f
 * https://chat.openai.com/share/3c1f06d8-79f4-4f36-8e3d-c4bf6509c6f7
 *
 * Security guidelines:
 * Android has built-in security features that significantly reduce the frequency and impact of application security issues.
 * The system is designed so that you can typically build your apps with the default system and file permissions and avoid difficult decisions about security.
 *
 * The Jetpack Security (JetSec) crypto library provides abstractions for encrypting Files and SharedPreferences objects. The library promotes the use of the
 * AndroidKeyStore while using safe and well-known cryptographic primitives. Using EncryptedFile and EncryptedSharedPreferences allows you to
 * locally protect files that may contain sensitive data, API keys, OAuth tokens, and other types of secrets.
 *
 * Key Generation:
 * Before we jump into encrypting your data, it’s important to understand how your encryption keys will be kept safe. Jetpack Security uses a master key,
 * which encrypts all subkeys that are used for each cryptographic operation. JetSec provides a recommended default master key in the MasterKeys class.
 * This class uses a basic AES256-GCM key which is generated and stored in the AndroidKeyStore. The AndroidKeyStore is a container which stores cryptographic
 * keys in the TEE or StrongBox, making them hard to extract. Subkeys are stored in a configurable SharedPreferences object.
 *
 * For apps that require more configuration, or handle very sensitive data, it’s recommended to build your KeyGenParameterSpec,
 * choosing options that make sense for your use. Time-bound keys with BiometricPrompt can provide an extra level of protection against rooted or
 * compromised devices.
 *
 * https://medium.com/androiddevelopers/data-encryption-on-android-with-jetpack-security-e4cb0b2d2a9
 * ****************
 * Question: If an android is already an secure OS then why do we need Jetpack Security library.
 * Answer: If device is rooted or compromised by some way then all data can be open to other person. So we need to make sure that data is secure.
 * In jetpack security :
 * 1> key security: If you have any key you dont want to get that stolen or in other word you want to make sure that it will be secure. So we are ahaving keystore
 * which is hardware backed. Which meant it runs in separate memory.
 */
import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.KeyStore
import javax.crypto.KeyGenerator

object KeyGenerator1 {

    private const val MASTER_KEY_ALIAS = "master_key_alias"

    // Generate a master key
    fun generateMasterKey(context: Context): MasterKey {
        //return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    // Generate a subkey using the master key
    fun generateSubKey(context: Context): String {
        //val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_shared_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences.getString("sub_key", null) ?: ""
    }
}


object KeyGenerator2 {

    private const val SUB_KEY_ALIAS = "sub_key_alias"

    // Generate a subkey using Android Keystore. It directly stores in android keystore
    // but it may be little bit costly. use it if really need to store more sensitive info.
    fun generateSubKey(context: Context): String {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        // Check if the key already exists
        if (!keyStore.containsAlias(SUB_KEY_ALIAS)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                SUB_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).apply {
                setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                setUserAuthenticationRequired(false) // Modify as per your security requirements
            }.build()

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }

        return keyStore.getKey(SUB_KEY_ALIAS, null).toString()
    }
}
