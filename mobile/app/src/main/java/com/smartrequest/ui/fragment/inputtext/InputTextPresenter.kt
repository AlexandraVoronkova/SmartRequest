package com.smartrequest.ui.fragment.inputtext

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.ui.other.resources.ResourceProvider
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@InjectViewState
class InputTextPresenter @Inject constructor(private val router: Router) : InputTextContract.Presenter() {

	//region ==================== MVP Presenter =====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
	}

	//endregion

	//region ==================== InputTextContract.Presenter ====================
	override fun onBackButtonClicked() {
		router.exit()
	}

	override fun onSendMessageButtonClicked(message: String) {
		router.exitWithResult(Screens.ResultCodes.RESULT_CODE_TEXT, message)
	}

	//endregion

	//region ==================== Internal =====================


	//endregion
}