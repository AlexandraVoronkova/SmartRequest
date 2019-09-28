package com.smartrequest.data.entities.auth

interface AuthCredentialsService {
	
	fun getAuthHeaders(): Map<String, String>

	fun performLogout()

	fun setOnLogoutRequiredListener(listener: Listener)

	interface Listener {

		fun onLogoutRequired()

	}
	
}