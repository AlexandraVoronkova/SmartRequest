package com.smartrequest.ui.activity.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter


interface MainScreenContract {

	@StateStrategyType(value = AddToEndSingleStrategy::class)
	interface View : MvpView {

		fun requestLocationPermissionsIfNotGranted()

		fun setUserHaveUnreadNotifications(haveUnreadNotifications: Boolean)

		fun setUserHaveUnreadMessages(haveUnreadMessages: Boolean)

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

		abstract fun onUserDataNavigationItemClicked()

		abstract fun onCallNavigationItemClicked()


		abstract fun onBackPressed()

	}

}