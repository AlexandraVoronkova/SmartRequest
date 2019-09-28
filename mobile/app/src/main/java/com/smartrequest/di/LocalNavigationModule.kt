package com.smartrequest.di

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import com.smartrequest.ui.navigation.LocalCiceroneHolder

@Module
class LocalNavigationModule {

	@Provides
	@Singleton
	internal fun provideLocalNavigationHolder(): LocalCiceroneHolder {
		return LocalCiceroneHolder()
	}
}
