package com.smartrequest.data.entities.auth

import android.content.Context
import android.support.annotation.Keep
import com.fasterxml.jackson.databind.ObjectMapper
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.smartrequest.data.entities.user.UserDataService
import com.smartrequest.data.entities.user.UserDataServiceImpl
import com.smartrequest.data.entities.user.model.UserData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(private val userDataService: UserDataService,
                                          private val credentialsStorage: CredentialsStorage,
                                          authCredentialsService: AuthCredentialsService) : AuthService,
        AuthCredentialsService.Listener {

    private val authSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()


    init {
        authCredentialsService.setOnLogoutRequiredListener(this)
    }

    //region ==================== UserService ====================

    override fun login(login: String, password: String): Completable {
        return Completable.create { emitter ->
            val userModel = userDataService.getUserByLogin(login)
            if (userModel == null) {
                emitter.onError(Throwable("Неверный логин или пароль"))
            } else {
                if (userModel.password != password) {
                    emitter.onError(Throwable("Неверный логин или пароль"))
                } else {
                    onUserLogin(userModel.user)
                    emitter.onComplete()
                }
            }
        }
    }

    override fun register(name: String, address: String, email: String, phoneNumber: String, password: String): Completable {
        return Completable.create { emitter ->
            val userModel =  userDataService.getUserByLogin(email)
            if (userModel != null) {
                emitter.onError(Throwable("Пользователь с таким email уже зарегистрирован"))
            } else {
                val user = UserData(Random().nextLong().toString(), name, address, email, phoneNumber)
                val userAuthModel = UserDataServiceImpl.UserAuthModel(user, password)
                userDataService.saveUserAuthModel(userAuthModel)
                onUserLogin(user)
                emitter.onComplete()
            }
        }
    }

    override fun logout(): Completable {
        return Completable.fromAction {
            performLogout()
        }
    }

    override fun isLoggedIn(): Boolean {
        return !credentialsStorage.getAccessToken().isNullOrEmpty()
    }

    override fun getAuthStatusObservable(): Observable<Boolean> {
        return authSubject
    }

    //endregion

    //region ==================== AuthCredentialsService.Listener ====================

    override fun onLogoutRequired() {
        performLogout()
    }

    //endregion

    //region ==================== Internal ====================

    private fun performLogout() {
        credentialsStorage.clearCredentials()
        authSubject.onNext(false)
    }

    private fun onUserLogin(user: UserData) {
        userDataService.saveCurrentUser(user)
        credentialsStorage.saveCredentials("Token")
        authSubject.onNext(true)
    }



    //endregion

}
