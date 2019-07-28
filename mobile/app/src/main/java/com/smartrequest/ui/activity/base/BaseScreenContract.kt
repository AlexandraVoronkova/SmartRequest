package com.smartrequest.ui.activity.base

import com.arellomobile.mvp.MvpView
import com.smartrequest.ui.fragment.base.BaseDisposablePresenter

interface BaseScreenContract {

	interface View : MvpView {

	}

	abstract class Presenter : BaseDisposablePresenter<View>() {

	}


}