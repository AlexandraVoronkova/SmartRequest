package com.smartrequest.ui.activity.authoptions

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [AuthOptionsScreenModule::class])
interface AuthOptionsScreenComponent {

	fun inject(activity: AuthOptionsScreenActivity)

}

@Module
class AuthOptionsScreenModule() {

	@Provides
	fun presenter(presenter: AuthOptionsScreenPresenter): AuthOptionsScreenContract.Presenter {
		return presenter
	}

}