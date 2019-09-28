package com.smartrequest.ui.fragment.userdata

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter
import com.smartrequest.ui.fragment.base.view.ViewWithPreloader
import java.util.*


interface UserDataContract {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    interface View : MvpView, ViewWithPreloader {

        fun setName(text: String?)

        fun setEmail(text: String?)

        fun setPhoneNumber(text: String?)

        fun setAddress(text: String?)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun requestTextInputValues()

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showErrorMessage(message: String)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun showSuccessMessage(message: String)

    }

    abstract class Presenter : BaseDisposablePresenter<View>() {

        abstract fun onSaveButtonClicked()

        abstract fun onAddressClicked()

        abstract fun onLogoutButtonClicked()

        abstract fun handleTextInputValues(name: String, address: String, email: String, phoneNumber: String)

    }

}