package com.smartrequest.ui.fragment.userdata

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [UserDataModule::class])
interface UserDataComponent {

	fun inject(fragment: UserDataFragment)

}

@Module
class UserDataModule() {
	@Provides
	fun presenter(editParentProfilePresenter: UserDataPresenter): UserDataContract.Presenter {
		return editParentProfilePresenter
	}


}
