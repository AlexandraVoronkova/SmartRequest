package com.smartrequest.ui.adapter

interface ViewModelMapper<T, K: ListViewModel?> {

	fun map(entity: T): K

}