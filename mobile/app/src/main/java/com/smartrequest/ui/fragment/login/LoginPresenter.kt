package com.smartrequest.ui.fragment.login

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.data.entities.auth.AuthService
import com.smartrequest.extensions.appspecific.getErrorMessage
import com.smartrequest.helpers.FormatHelper
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.ui.other.resources.ResourceProvider
import com.smartrequest.utils.RxUtils
import io.reactivex.Completable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(private val router: Router,
										 private val authService: AuthService,
										 private val resourceProvider: ResourceProvider) : LoginContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		viewState.showInitialMessageIfExists()
	}

	//endregion

	//region ==================== LoginContract.Presenter ====================

	override fun onCloseButtonClicked() {
		router.exit()
	}

	override fun onInputValuesChanged(login: String, password: String) {
		viewState.setLoginButtonEnabled(isInputValuesValid(login, password))
	}

	override fun onAuthorizationButtonClicked(login: String, password: String) {
		if (!isInputValuesValid(login, password)) {
			return
		}
		viewState.showPreloader()
		val disposable = authService.login(login, password)
				.compose(RxUtils.applyCompletableSchedulers())
				.subscribe({
					viewState.hidePreloader()
					router.newRootScreen(Screens.MAIN_SCREEN_CONTAINER)
				}, {
					viewState.hidePreloader()
					it.printStackTrace()
					it.message?.let {
						viewState?.showErrorMessage(it)
					}
				})
		compositeDisposable.add(disposable)
	}

	override fun onRegistrationButtonClicked() {
		router.replaceScreen(Screens.REGISTRATION_SCREEN)
	}

	//endregion

	//region ===================== Internal ======================

	private fun isInputValuesValid(login: String, password: String): Boolean {
		return FormatHelper.isValidEmail(login) && password.isNotBlank()
	}

	//endregion


}