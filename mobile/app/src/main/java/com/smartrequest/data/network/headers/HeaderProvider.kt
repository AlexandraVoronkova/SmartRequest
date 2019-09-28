package com.smartrequest.data.network.headers

data class RequestHeader(val name: String, val value: String)

interface HeaderProvider {

	fun provideHeader(): com.smartrequest.data.network.headers.RequestHeader

}