package com.smartrequest.ui.activity.auth

import com.arellomobile.mvp.InjectViewState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class AuthScreenPresenter @Inject constructor(private val router: Router,
                                              @Named("auth_start_screen") private val startScreen: String,
                                              @Named("start_screen_data") private val startScreenData: Any?) : AuthScreenContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		router.replaceScreen(startScreen, startScreenData)
	}

	//endregion

	//region ==================== AuthScreenContract.Presenter ====================


	//endregion

}