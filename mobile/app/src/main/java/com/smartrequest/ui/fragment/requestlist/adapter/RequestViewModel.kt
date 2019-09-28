package com.smartrequest.ui.fragment.requestlist.adapter

import com.smartrequest.ui.adapter.ListViewModel


data class RequestViewModel(override var listItemId: String?,
							val name: String,
							val status: String) : ListViewModel