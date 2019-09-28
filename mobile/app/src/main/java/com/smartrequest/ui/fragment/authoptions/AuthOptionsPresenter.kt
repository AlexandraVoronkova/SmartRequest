package com.smartrequest.ui.fragment.authoptions

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.ui.navigation.AppScreenContainerParams
import com.smartrequest.ui.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AuthOptionsPresenter @Inject constructor(private val router: Router) : AuthOptionsContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()

	}

	//endregion

	//region ==================== AuthOptionsContract.Presenter ====================

	override fun onRegisterButtonClicked() {
		router.navigateTo(Screens.AUTH_SCREEN_CONTAINER, AppScreenContainerParams(Screens.REGISTRATION_SCREEN))
	}

	override fun onLoginButtonClicked() {
		router.navigateTo(Screens.AUTH_SCREEN_CONTAINER, AppScreenContainerParams(Screens.LOGIN_SCREEN))
	}

	//endregion


}