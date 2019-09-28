package com.smartrequest.ui.fragment.call

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.data.entities.category.model.Category
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter
import com.smartrequest.ui.fragment.base.view.ViewWithPreloader
import java.util.*


interface CallContract {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    interface View : MvpView, ViewWithPreloader {

        fun setCategory(category: String)

        fun setComment(comment: String)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showErrorMessage(message: String)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showSuccessMessage(message: String)


    }

    abstract class Presenter : BaseDisposablePresenter<View>() {

        abstract fun onBackButtonClicked()

        abstract fun onCategoryButtonClicked()

        abstract fun onCommentButtonClicked(comment: String)

        abstract fun onSendButtonClicked()

    }

}