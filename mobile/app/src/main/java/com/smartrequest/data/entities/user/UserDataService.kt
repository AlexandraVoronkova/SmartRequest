package com.smartrequest.data.entities.user

import com.smartrequest.data.entities.user.model.UserData

interface UserDataService {

	fun saveUser(userData: UserData)

	fun saveCurrentUser(userData: UserData)

	fun getUser(): UserData?

	fun getUserByLogin(login: String): UserDataServiceImpl.UserAuthModel?

	fun saveUserAuthModel(userAuthModel: UserDataServiceImpl.UserAuthModel)
}