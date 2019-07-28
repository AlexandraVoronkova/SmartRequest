package com.smartrequest.ui.fragment.base

import android.support.annotation.StringRes
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.smartrequest.helpers.KeyboardHelper
import com.smartrequest.ui.activity.base.BaseActivity
import com.smartrequest.ui.navigation.RouterProvider
import ru.terrakok.cicerone.Router

open class BaseDialogFragment : MvpAppCompatDialogFragment() {
	
	//region ==================== Lifecycle ====================
	
	override fun onPause() {
		super.onPause()
		KeyboardHelper.hideSoftKeyboard(activity)
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
}