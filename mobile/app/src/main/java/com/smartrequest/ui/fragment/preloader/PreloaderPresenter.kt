package com.smartrequest.ui.fragment.preloader

import com.arellomobile.mvp.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PreloaderPresenter @Inject constructor(private val router: Router) : PreloaderContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()

	}

	//endregion

	//region ==================== PreloaderContract.Presenter ====================


	//endregion


}