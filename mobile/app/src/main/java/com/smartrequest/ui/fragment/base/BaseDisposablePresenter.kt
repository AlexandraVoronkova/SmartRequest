package com.smartrequest.ui.fragment.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.smartrequest.utils.RxUtils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Holds [Disposable] object and perform disposal in onDestroy()
 */
abstract class BaseDisposablePresenter<T : MvpView> : MvpPresenter<T>() {

	protected val compositeDisposable = CompositeDisposable()


	override fun onDestroy() {
		super.onDestroy()
		RxUtils.disposeIfNeeded(compositeDisposable)
	}
}