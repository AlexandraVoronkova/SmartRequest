package com.smartrequest.ui.fragment.inputtext

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [InputTextModule::class])
interface InputTextComponent {

	fun inject(fragment: InputTextFragment)

}

@Module
class InputTextModule {

	@Provides
	fun presenter(inputTextPresenter: InputTextPresenter): InputTextContract.Presenter {
		return inputTextPresenter
	}


}