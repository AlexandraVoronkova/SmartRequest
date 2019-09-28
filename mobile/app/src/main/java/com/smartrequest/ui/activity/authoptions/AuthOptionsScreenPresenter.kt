package com.smartrequest.ui.activity.authoptions

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.ui.navigation.Screens
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class AuthOptionsScreenPresenter @Inject constructor(private val router: Router) : AuthOptionsScreenContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		router.replaceScreen(Screens.AUTH_OPTIONS_SCREEN)
	}

	//endregion

	//region ==================== AuthOptionsScreenContract.Presenter ====================


	//endregion

}