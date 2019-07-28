package com.smartrequest.ui.fragment.registration

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter
import com.smartrequest.ui.fragment.base.view.ViewWithPreloader


interface RegistrationContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView, ViewWithPreloader {

		fun setAddress(text: String?)

		fun setRegistrationButtonEnabled(enabled: Boolean)

		@StateStrategyType(OneExecutionStateStrategy::class)
		fun showErrorMessage(message: String)

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

		abstract fun onAddressClicked()

		abstract fun onCloseButtonClickListener()

		abstract fun onInputValuesChanged(name: String,
										  address: String,
		                                  email: String,
		                                  phoneNumber: String,
		                                  password: String)

		abstract fun onRegistrationButtonClicked(name: String,
												 address: String,
		                                         email: String,
		                                         phoneNumber: String,
		                                         password: String)

		abstract fun onAuthorizationButtonClicked()

	}

}