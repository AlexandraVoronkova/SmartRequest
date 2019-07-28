package com.smartrequest.ui.fragment.base

import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.smartrequest.R
import com.smartrequest.helpers.DimensHelper
import com.smartrequest.helpers.KeyboardHelper
import com.smartrequest.ui.activity.base.BaseActivity
import com.smartrequest.ui.navigation.RouterProvider
import com.smartrequest.utils.DeviceUtils
import ru.terrakok.cicerone.Router
import android.content.Intent



open class BaseFragment : MvpAppCompatFragment() {
	
	//region ==================== Lifecycle ====================
	
	override fun onPause() {
		super.onPause()
		KeyboardHelper.hideSoftKeyboard(activity)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		val fragments = childFragmentManager.fragments
		for (fragment in fragments) {
			fragment?.onActivityResult(requestCode, resultCode, data)
		}
	}
	
	//endregion
	
	//region ==================== Toast ====================

	protected fun showSnackBar(message: String, actionText: String? = null, action: View.OnClickListener? = null, container: View? = null) {
		(activity as BaseActivity?)?.showSnackBar(message, actionText, action, container)
	}
	
	protected fun showToastMessage(message: String?) {
		message?.let {
			activity?.let {
				Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
			}
			
		}
	}
	
	protected fun showToastMessage(@StringRes stringResId: Int) {
		activity?.let {
			Toast.makeText(it, stringResId, Toast.LENGTH_SHORT).show()
		}
	}
	
	//endregion

	//region ==================== DI ====================

	fun getAppComponent(): com.smartrequest.di.AppComponent {
		return com.smartrequest.components.AppContext.instance.appComponent
	}

	protected fun getParentRouter(): Router? {
		parentFragment?.let { parentFragment ->
			if (parentFragment is RouterProvider) {
				return parentFragment.getRouter()
			}
		}

		return null
	}

	//endregion

	//region ==================== Toolbar configuration ====================

	fun setupToolbar(inflatedView: View, titleId: Int?, homeIconId: Int?, backButtonEnabled: Boolean,
	                 toolbarNavigationButtonClickListener: View.OnClickListener?) {
		setupToolbar(inflatedView, titleId?.let { resources.getString(it) }, homeIconId, backButtonEnabled, toolbarNavigationButtonClickListener)
	}

	fun setupToolbar(inflatedView: View, title: String?, homeIconId: Int?, backButtonEnabled: Boolean,
	                 toolbarNavigationButtonClickListener: View.OnClickListener?) {
		val toolbar = inflatedView.findViewById<Toolbar>(R.id.toolbar)
		if (toolbar != null) {
			val activity = activity as? AppCompatActivity
			if (activity != null) {
				activity.setSupportActionBar(toolbar)
				activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
				toolbar.title = title ?: ""
				if (homeIconId != null) {
					toolbar.setNavigationIcon(homeIconId)
				} else if (backButtonEnabled) {
					activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
				} else {
					activity.supportActionBar!!.setDisplayShowHomeEnabled(false)
					toolbar.setContentInsetsRelative(toolbar.contentInsetLeft +
							DimensHelper.dpToPx(context!!, 0.0f).toInt(),
							toolbar.contentInsetRight)
				}
				activity.setSupportActionBar(toolbar)
				toolbar.setNavigationOnClickListener(toolbarNavigationButtonClickListener)
			}
		}
	}

	protected fun positionToolbarBelowStatusBar(inflatedView: View) {
		val toolbar = inflatedView.findViewById<Toolbar>(R.id.toolbar)
		if (toolbar != null) {
			toolbar.layoutParams = (toolbar.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
				topMargin = DeviceUtils.getStatusBarHeight(resources)
			}
		}
	}

	//endregion
	
}