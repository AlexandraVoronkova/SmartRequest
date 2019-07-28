package com.smartrequest.ui.fragment.inputtext

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter

interface InputTextContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView {

		@StateStrategyType(OneExecutionStateStrategy::class)
		fun showErrorMessage(message: String)

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

		abstract fun onBackButtonClicked()

		abstract fun onSendMessageButtonClicked(message: String)

	}

}