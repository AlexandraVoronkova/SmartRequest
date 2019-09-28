package com.smartrequest.ui.fragment.call

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [CallModule::class])
interface CallComponent {

	fun inject(fragment: CallFragment)

}

@Module
class CallModule() {
	@Provides
	fun presenter(callPresenter: CallPresenter): CallContract.Presenter {
		return callPresenter
	}


}
