package com.smartrequest.ui.fragment.registration

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Subcomponent(modules = [RegistrationModule::class])
interface RegistrationComponent {

	fun inject(fragment: RegistrationFragment)

}

@Module
class RegistrationModule {

	@Provides
	fun presenter(registrationPresenter: RegistrationPresenter): RegistrationContract.Presenter {
		return registrationPresenter
	}


}