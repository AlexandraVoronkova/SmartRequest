package com.smartrequest.data.entities.auth

import com.fasterxml.jackson.core.type.TypeReference
import com.smartrequest.R
import com.smartrequest.ui.other.resources.ResourceProvider
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Named

class AuthCredentialsServiceImpl @Inject constructor(private val credentialsStorage: CredentialsStorage,
													 private val resourceProvider: ResourceProvider,
                                                     @Named("nonAuth") private val apiClient: com.smartrequest.data.network.api.ApiClient): AuthCredentialsService {

	private var listener: AuthCredentialsService.Listener? = null

	//region ===================== AuthCredentialsService ======================

	override fun getAuthHeaders(): Map<String, String> {
		return mapOf(Pair("Authorization", "Token " + resourceProvider.getString(R.string.dadata_api)))
	}

	override fun performLogout() {
		listener?.onLogoutRequired()
	}

	override fun setOnLogoutRequiredListener(listener: AuthCredentialsService.Listener) {
		this.listener = listener
	}

	//endregion


}