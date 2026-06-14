package ru.makoto.fefustore.Data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import ru.makoto.fefustore.BuildConfig

class SecretsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secret_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val apiKeyMigrated = false

    fun saveSecret(key: String, value: String) {
        prefs.edit { putString(key, value) }
    }

    fun getSecret(key: String): String? {
        return prefs.getString(key, null)
    }

    fun hasSecret(key: String): Boolean {
        return prefs.contains(key)
    }

    fun removeSecret(key: String) {
        prefs.edit { remove(key) }
    }

    fun getApiKey(): String? {
        if (hasSecret("api.key")) {
            return getSecret("api.key")
        }
        val key = BuildConfig.API_KEY

        if (key.isEmpty()) {
            throw IllegalStateException("API key not configured")
        }
        saveSecret("api.key", key)
        return key
    }
}