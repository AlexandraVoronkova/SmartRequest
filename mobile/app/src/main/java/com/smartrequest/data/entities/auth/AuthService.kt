package com.smartrequest.data.entities.auth

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface AuthService {

	fun login(login: String, password: String): Completable

	fun register(name: String, address: String, email: String, phoneNumber: String, password: String): Completable

	fun logout(): Completable

	fun isLoggedIn(): Boolean

	fun getAuthStatusObservable(): Observable<Boolean>

}