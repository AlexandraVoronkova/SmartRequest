package com.smartrequest.ui.fragment.authoptions

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [AuthOptionsModule::class])
interface AuthOptionsComponent {

	fun inject(fragment: AuthOptionsFragment)

}

@Module
class AuthOptionsModule() {

	@Provides
	fun presenter(authOptionsPresenter: AuthOptionsPresenter): AuthOptionsContract.Presenter {
		return authOptionsPresenter
	}


}