package com.smartrequest.ui.activity.splash

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter


interface SplashScreenContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView {
	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

		abstract fun onScreenLoadedWithoutExtraParams()

		abstract fun onExtraParamsReceived(data: Map<String, Any>)

	}

}