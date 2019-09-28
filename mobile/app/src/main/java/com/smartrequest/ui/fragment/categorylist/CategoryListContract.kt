package com.smartrequest.ui.fragment.categorylist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter
import com.smartrequest.ui.fragment.base.view.ViewWithPreloader


interface CategoryListContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView, ViewWithPreloader {

        fun showItemlist(items: List<ListViewModel>)

        @StateStrategyType(OneExecutionStateStrategy::class)
		fun showErrorMessage(message: String)

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

        abstract fun onBackButtonClicked()

        abstract fun onItemClicked(item: ListViewModel)

	}

}