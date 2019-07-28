package com.smartrequest.ui.fragment.authoptions

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter


interface AuthOptionsContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView {

		@StateStrategyType(OneExecutionStateStrategy::class)
		fun showMessage(message: String)

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

		abstract fun onRegisterButtonClicked()

		abstract fun onLoginButtonClicked()

	}

}