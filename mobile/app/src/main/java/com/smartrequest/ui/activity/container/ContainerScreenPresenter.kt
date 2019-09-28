package com.smartrequest.ui.activity.container

import com.arellomobile.mvp.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class ContainerScreenPresenter @Inject constructor(private val router: Router,
                                                   @Named("start_screen") private val startScreen: String?,
                                                   @Named("start_screen_data") private val startScreenData: Any?) : ContainerScreenContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		router.replaceScreen(startScreen, startScreenData)
	}

	//endregion

	//region ==================== ContainerScreenContract.Presenter ====================


	//endregion

}