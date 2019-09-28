package com.smartrequest.ui.activity.splash

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [SplashScreenModule::class])
interface SplashScreenComponent {

	fun inject(activity: SplashScreenActivity)

}

@Module
class SplashScreenModule {

	@Provides
	fun presenter(presenter: SplashScreenPresenter): SplashScreenContract.Presenter {
		return presenter
	}

}