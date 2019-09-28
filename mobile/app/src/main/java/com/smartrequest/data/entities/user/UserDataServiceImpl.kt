package com.smartrequest.data.entities.user

import android.content.Context
import android.support.annotation.Keep
import com.fasterxml.jackson.databind.ObjectMapper
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.smartrequest.data.entities.user.model.UserData
import javax.inject.Inject

class UserDataServiceImpl @Inject constructor(private val context: Context,
											  private val objectMapper: ObjectMapper ) : UserDataService {

	companion object {

		const val PREFERENCES_FILENAME = "user_data"

		const val KEY_USER_DATA = "KEY_USER_DATA"
	}

	private val preferences = BinaryPreferencesBuilder(context)
			.name(PREFERENCES_FILENAME)
			.build()

	override fun saveUser(userData: UserData) {

		var oldLogin: String?
		preferences.getString(KEY_USER_DATA, null)?.let {
			oldLogin = objectMapper.readValue(it, UserData::class.java).email

			preferences.getString(oldLogin, null)?.let {
				val userAuthModel = objectMapper.readValue(it, UserAuthModel::class.java)
				userAuthModel.user = userData

				preferences.edit()
						.remove(oldLogin)
						.putString(userData.email, objectMapper.writeValueAsString(userAuthModel))
						.putString(KEY_USER_DATA, objectMapper.writeValueAsString(userAuthModel.user))
						.apply()
			}
		}
	}

	override fun saveCurrentUser(userData: UserData) {
		preferences.edit()
				.putString(KEY_USER_DATA, objectMapper.writeValueAsString(userData))
				.apply()
	}

	override fun getUser(): UserData? {
		return preferences.getString(KEY_USER_DATA, null)?.let {
			objectMapper.readValue(it, UserData::class.java)
		}
	}

	override fun getUserByLogin(login: String): UserAuthModel? {
		return preferences.getString(login, null)?.let {
			objectMapper.readValue(it, UserAuthModel::class.java)
		}
	}

	override fun saveUserAuthModel(userAuthModel: UserAuthModel) {
		preferences.edit()
				.putString(userAuthModel.user.email, objectMapper.writeValueAsString(userAuthModel))
				.apply()
	}

	//!!Имитация базы данных!!
	@Keep
	data class UserAuthModel(var user: UserData,
							 val password: String)
}
