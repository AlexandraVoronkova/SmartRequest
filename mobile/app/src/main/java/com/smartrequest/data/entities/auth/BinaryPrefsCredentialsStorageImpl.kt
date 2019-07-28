package com.smartrequest.data.entities.auth

import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import javax.inject.Inject

class BinaryPrefsCredentialsStorageImpl @Inject constructor(context: Context) : CredentialsStorage {

	companion object {

		const val PREFERENCES_FILENAME = "credentials"

		const val KEY_ACCESS_TOKEN = "access_token"

	}

	private val preferences = BinaryPreferencesBuilder(context)
			.name(PREFERENCES_FILENAME)
			.build()

	//region ==================== CredentialsStorage ====================

	override fun saveCredentials(accessToken: String) {
		preferences.edit()
				.putString(KEY_ACCESS_TOKEN, accessToken)
				.apply()
	}

	override fun getAccessToken(): String? {
		return preferences.getString(KEY_ACCESS_TOKEN, null)
	}

	override fun clearCredentials() {
		preferences.edit()
				.remove(KEY_ACCESS_TOKEN)
				.apply()
	}

	//endregion

}