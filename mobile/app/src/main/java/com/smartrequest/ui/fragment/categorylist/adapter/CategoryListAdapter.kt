package com.smartrequest.ui.fragment.categorylist.adapter

import com.smartrequest.ui.adapter.DiffAdapter
import javax.inject.Inject

class CategoryListAdapter @Inject constructor(categoryItemViewModelDelegate: CategoryItemViewModelDelegate) : DiffAdapter() {

	init {
		delegatesManager.addDelegate(categoryItemViewModelDelegate)

	}

}

