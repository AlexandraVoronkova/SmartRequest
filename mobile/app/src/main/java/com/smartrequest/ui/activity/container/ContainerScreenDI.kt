package com.smartrequest.ui.activity.container

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [ContainerScreenModule::class])
interface ContainerScreenComponent {

	fun inject(activity: ContainerScreenActivity)

}

@Module
class ContainerScreenModule(private val startScreen: String?,
                            private val startScreenData: Any?) {

	@Provides
	@Named("start_screen")
	fun startScreen(): String? {
		return startScreen
	}

	@Provides
	@Named("start_screen_data")
	fun startScreenData(): Any? {
		return startScreenData
	}

	@Provides
	fun presenter(presenter: ContainerScreenPresenter): ContainerScreenContract.Presenter {
		return presenter
	}

}