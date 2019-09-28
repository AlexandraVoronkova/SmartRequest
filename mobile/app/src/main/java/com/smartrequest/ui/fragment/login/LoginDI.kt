package com.smartrequest.ui.fragment.login

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

	fun inject(fragment: LoginFragment)

}

@Module
class LoginModule() {

	@Provides
	fun presenter(loginPresenter: LoginPresenter): LoginContract.Presenter {
		return loginPresenter
	}


}