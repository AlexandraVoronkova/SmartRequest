package com.smartrequest.ui.fragment.requestlist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter
import com.smartrequest.ui.fragment.base.view.ViewWithPreloader


interface RequestListContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView, ViewWithPreloader {

        fun showItemlist(items: List<ListViewModel>)

		fun setEmptyState(isVisible: Boolean)

        @StateStrategyType(OneExecutionStateStrategy::class)
		fun showErrorMessage(message: String)

		@StateStrategyType(OneExecutionStateStrategy::class)
		fun showSuccessMessage(message: String)

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

		abstract fun onAddRequestClicked()

        abstract fun onItemClicked(item: ListViewModel)

	}

}