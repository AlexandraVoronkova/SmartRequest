package com.smartrequest.ui.activity.main

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.ui.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainScreenPresenter @Inject constructor(private val router: Router) :
		MainScreenContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		router.replaceScreen(Screens.BOTTOM_TAB_USER_DATA_SCREEN)
	}

	//endregion

	//region ==================== MainScreenContract.Presenter ====================


	override fun onCallNavigationItemClicked() {
		router.replaceScreen(Screens.BOTTOM_TAB_CALL_SCREEN)
	}

	override fun onUserDataNavigationItemClicked() {
		router.replaceScreen(Screens.BOTTOM_TAB_USER_DATA_SCREEN)
	}

	override fun onBackPressed() {
		router.exit()
	}

	//endregion

}