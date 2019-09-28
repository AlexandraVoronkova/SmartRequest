package com.smartrequest.ui.fragment.categorylist.adapter

import com.smartrequest.ui.adapter.ListViewModel


data class CategoryItemViewModel(override var listItemId: String?,
								 val name: String,
								 val isParent: Boolean) : ListViewModel