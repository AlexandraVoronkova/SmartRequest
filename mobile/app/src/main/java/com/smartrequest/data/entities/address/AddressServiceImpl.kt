package com.smartrequest.data.entities.address

import android.support.annotation.Keep
import com.fasterxml.jackson.core.type.TypeReference
import com.smartrequest.data.entities.address.model.Address
import com.smartrequest.data.network.api.ApiClient
import io.reactivex.Single
import javax.inject.Inject

class AddressServiceImpl @Inject constructor(private val apiClient: ApiClient) : AddressService {

	companion object {

		const val URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address"
	}

	override fun getAddressList(searchString: String): Single<List<Address>> {
		val params = mutableMapOf<String, Any>().apply {
			put("query", searchString)
			put("count", searchString.length)
		}
		return apiClient.get(URL, params, object : TypeReference<AddressListRequest>() {})
				.map { it.suggestions }
	}

	@Keep
	data class AddressListRequest(val suggestions: List<Address>)

}
