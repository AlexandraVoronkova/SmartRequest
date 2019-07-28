package com.smartrequest.ui.fragment.base.view

import android.support.v4.app.FragmentManager
import com.smartrequest.ui.fragment.preloader.PreloaderFragment

interface FragmentWithPreloader: ViewWithPreloader {

	fun getChildFragmentManager(): FragmentManager

	override fun showPreloader() {
		PreloaderFragment.show(getChildFragmentManager())
	}

	override fun hidePreloader() {
		PreloaderFragment.hide(getChildFragmentManager())
	}

}



