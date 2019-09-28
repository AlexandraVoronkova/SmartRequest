package com.smartrequest.ui.fragment.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter
import com.smartrequest.ui.fragment.base.view.ViewWithPreloader


interface LoginContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView, ViewWithPreloader {

		fun showInitialMessageIfExists()

		fun setLoginButtonEnabled(enabled: Boolean)

		@StateStrategyType(OneExecutionStateStrategy::class)
		fun showErrorMessage(message: String)

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

		abstract fun onCloseButtonClicked()

		abstract fun onInputValuesChanged(login: String, password: String)

		abstract fun onAuthorizationButtonClicked(login: String, password: String)

		abstract fun onRegistrationButtonClicked()

	}

}