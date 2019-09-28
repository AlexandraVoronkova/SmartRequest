package com.smartrequest.ui.activity.auth

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [AuthScreenModule::class])
interface AuthScreenComponent {

	fun inject(activity: AuthScreenActivity)

}

@Module
class AuthScreenModule(private val startScreen: String,
                       private val startScreenData: Any?) {

	@Provides
	@Named("auth_start_screen")
	fun authStartScreen(): String {
		return startScreen
	}


	@Provides
	@Named("start_screen_data")
	fun startScreenData(): Any? {
		return startScreenData
	}

	@Provides
	fun presenter(presenter: AuthScreenPresenter): AuthScreenContract.Presenter {
		return presenter
	}

}