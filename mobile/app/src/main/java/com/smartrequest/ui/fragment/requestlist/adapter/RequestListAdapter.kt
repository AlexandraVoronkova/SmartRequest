package com.smartrequest.ui.fragment.requestlist.adapter

import com.smartrequest.ui.adapter.DiffAdapter
import javax.inject.Inject

class RequestListAdapter @Inject constructor(requestViewModelDelegate: RequestViewModelDelegate) : DiffAdapter() {

	init {
		delegatesManager.addDelegate(requestViewModelDelegate)

	}

}

