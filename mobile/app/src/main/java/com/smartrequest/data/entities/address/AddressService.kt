package com.smartrequest.data.entities.address

import com.smartrequest.data.entities.address.model.Address
import io.reactivex.Single

interface AddressService {

	fun getAddressList(searchString: String): Single<List<Address>>
	
}