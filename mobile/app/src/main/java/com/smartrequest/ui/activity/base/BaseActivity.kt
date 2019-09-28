package com.smartrequest.ui.activity.base

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.crashlytics.android.Crashlytics
import com.smartrequest.helpers.KeyboardHelper
import javax.inject.Provider


open class BaseActivity : MvpAppCompatActivity(), BaseScreenContract.View {

	@InjectPresenter
	lateinit var baseScreenPresenter: BaseScreenContract.Presenter

	private lateinit var baseScreenPresenterProvider: Provider<BaseScreenContract.Presenter>
	
	//region ==================== Lifecycle callbacks ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}
	
	override fun onPostCreate(@Nullable savedInstanceState: Bundle?) {
		super.onPostCreate(savedInstanceState)
		
		supportActionBar?.apply {
			setDisplayShowTitleEnabled(true)
			setDisplayShowHomeEnabled(true)
			setDisplayHomeAsUpEnabled(true)
			setHomeButtonEnabled(true)
		}
	}
	
	//region ==================== Lifecycle ====================
	
	override fun onPause() {
		super.onPause()
		KeyboardHelper.hideSoftKeyboard(this)
	}
	
	//endregion
	
	//endregion
	
	//region ==================== Options menu ====================
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item?.itemId) {
			android.R.id.home -> {
				finish()
				return true
			}
			else -> return super.onOptionsItemSelected(item)
		}
	}
	
	//endregion

	//region ==================== DI ====================

	fun getAppComponent(): com.smartrequest.di.AppComponent {
		return com.smartrequest.components.AppContext.instance.appComponent
	}

	private fun configureDI() {
		val component = getAppComponent().plus(BaseScreenModule(shouldMonitorAuthStatus()))
		baseScreenPresenterProvider = component.provideBaseScreenPresenter()
	}

	@ProvidePresenter
	internal fun provideBaseScreenPresenter(): BaseScreenContract.Presenter {
		return baseScreenPresenterProvider.get()
	}

	protected open fun shouldMonitorAuthStatus(): Boolean {
		return true
	}

	//endregion
	
	//region ==================== Protected ====================
	
	fun showSnackBar(message: String, actionText: String? = null, action: View.OnClickListener? = null, container: View? = null) {
		val snackbar = Snackbar.make(container
				?: window.decorView.findViewById(android.R.id.content), message, Snackbar
				.LENGTH_SHORT)
		if (actionText != null) {
			snackbar.setAction(actionText, action)
			snackbar.duration = Snackbar.LENGTH_INDEFINITE
		}
		try {
			val snackbarView = snackbar.getView()
			val textView = snackbarView.findViewById<TextView>(android.support.design.R.id
					.snackbar_text)
			textView.maxLines = 5  // show multiple line
		} catch (e: Exception) {
			e.printStackTrace()
			Crashlytics.logException(e)
		}
		
		snackbar.show()
	}
	
	//endregion
	
}