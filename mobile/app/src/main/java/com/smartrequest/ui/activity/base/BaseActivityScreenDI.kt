package com.smartrequest.ui.activity.base

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named
import javax.inject.Provider

@Subcomponent(modules = [BaseScreenModule::class])
interface BaseScreenComponent {

	fun provideBaseScreenPresenter(): Provider<BaseScreenContract.Presenter>
	
}

@Module
class BaseScreenModule(private val shouldMonitorAuthStatus: Boolean) {

	@Named("monitor_auth_status")
	@Provides
	fun shouldMonitorAuthStatus(): Boolean {
		return shouldMonitorAuthStatus
	}

	@Provides
	fun presenter(baseScreenPresenter: BaseScreenPresenter): BaseScreenContract.Presenter {
		return baseScreenPresenter
	}
	
}