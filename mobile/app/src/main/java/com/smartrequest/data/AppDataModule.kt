package com.smartrequest.data

import com.smartrequest.data.entities.address.AddressService
import com.smartrequest.data.entities.address.AddressServiceImpl
import com.smartrequest.data.entities.category.CategoryService
import com.smartrequest.data.entities.category.CategoryServiceImpl
import com.smartrequest.data.entities.request.RequestService
import com.smartrequest.data.entities.request.RequestServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDataModule {

	//region ==================== Auth ====================

	@Provides
	@Singleton
	fun provideCredentialsStorage(
			credentialsStorage: com.smartrequest.data.entities.auth.BinaryPrefsCredentialsStorageImpl): com.smartrequest.data.entities.auth.CredentialsStorage {
		return credentialsStorage
	}

	@Provides
	@Singleton
	fun provideAuthService(authServiceImpl: com.smartrequest.data.entities.auth.AuthServiceImpl): com.smartrequest.data.entities.auth.AuthService {
		return authServiceImpl
	}

	@Provides
	@Singleton
	fun provideAuthCredentialsService(authCredentialsService: com.smartrequest.data.entities.auth.AuthCredentialsServiceImpl): com.smartrequest.data.entities.auth.AuthCredentialsService {
		return authCredentialsService
	}

	//endregion

	//region ===================== Files ======================

	@Provides
	@Singleton
	fun provideFileService(fileServiceImpl: com.smartrequest.data.entities.file.FileServiceImpl): com.smartrequest.data.entities.file.FileService {
		return fileServiceImpl
	}

	//endregion


	//region ===================== UserDataService ======================

	@Provides
	@Singleton
	fun provideUserDataService(userDataServiceImpl: com.smartrequest.data.entities.user.UserDataServiceImpl): com.smartrequest.data.entities.user.UserDataService {
		return userDataServiceImpl
	}

	//endregion

	//region ===================== CategoryService ======================

	@Provides
	@Singleton
	fun provideCategoryService(categoryServiceImpl: CategoryServiceImpl): CategoryService {
		return categoryServiceImpl
	}

	//endregion

	//region ===================== AddressService ======================

	@Provides
	@Singleton
	fun provideAddressService(addressServiceImpl: AddressServiceImpl): AddressService {
		return addressServiceImpl
	}

	//endregion

	//region ===================== RequestService ======================

	@Provides
	@Singleton
	fun provideRequestService(requestServiceImpl: RequestServiceImpl): RequestService {
		return requestServiceImpl
	}

	//endregion

}