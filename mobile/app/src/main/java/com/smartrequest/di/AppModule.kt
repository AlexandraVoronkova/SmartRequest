package com.smartrequest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import com.smartrequest.ui.other.resources.ResourceProvider
import com.smartrequest.ui.other.resources.ResourceProviderImpl
import javax.inject.Singleton

@Module
class AppModule(private val application: com.smartrequest.components.AppContext) {
	
	@Provides
	@Singleton
	fun provideContext(): Context {
		return application
	}

	@Provides
	@Singleton
	fun provideResourceProvider(resourceProviderImpl: ResourceProviderImpl): ResourceProvider {
		return resourceProviderImpl
	}

}