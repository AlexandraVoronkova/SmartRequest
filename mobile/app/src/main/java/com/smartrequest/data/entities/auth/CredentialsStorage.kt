package com.smartrequest.data.entities.auth

interface CredentialsStorage {
	
	fun saveCredentials(accessToken: String)
	
	fun getAccessToken(): String?

	fun clearCredentials()
}