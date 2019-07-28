package com.smartrequest.ui.fragment.preloader

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter


interface PreloaderContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView {

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

	}

}