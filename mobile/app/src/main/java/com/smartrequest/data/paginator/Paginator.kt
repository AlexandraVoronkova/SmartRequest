package com.smartrequest.data.paginator

import io.reactivex.Observable

interface Paginator<T> {

	fun canLoadNext(): Boolean

	fun loadNext()

	fun itemsObservable(): Observable<List<T>>

	fun reset()


}