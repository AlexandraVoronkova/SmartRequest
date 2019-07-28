package com.smartrequest.ui.fragment.preloader

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [PreloaderModule::class])
interface PreloaderComponent {

	fun inject(fragment: PreloaderFragment)

}

@Module
class PreloaderModule {

	@Provides
	fun presenter(preloaderPresenter: PreloaderPresenter): PreloaderContract.Presenter {
		return preloaderPresenter
	}


}