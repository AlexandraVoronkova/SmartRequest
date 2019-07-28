package com.smartrequest.ui.fragment.addresslist.adapter

import com.smartrequest.ui.adapter.ListViewModel


data class AddressItemViewModel(override var listItemId: String?,
								val name: String) : ListViewModel