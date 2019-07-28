package com.smartrequest.ui.activity.main

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [MainScreenModule::class])
interface MainScreenComponent {

	fun inject(activity: MainActivity)

}

@Module
class MainScreenModule {

	@Provides
	fun presenter(presenter: MainScreenPresenter): MainScreenContract.Presenter {
		return presenter
	}

}