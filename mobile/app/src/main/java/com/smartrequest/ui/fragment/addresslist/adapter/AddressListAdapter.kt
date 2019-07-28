package com.smartrequest.ui.fragment.addresslist.adapter

import com.smartrequest.ui.adapter.DiffAdapter
import javax.inject.Inject

class AddressListAdapter @Inject constructor(addressItemViewModelDelegate: AddressItemViewModelDelegate) : DiffAdapter() {

	init {
		delegatesManager.addDelegate(addressItemViewModelDelegate)

	}

}

