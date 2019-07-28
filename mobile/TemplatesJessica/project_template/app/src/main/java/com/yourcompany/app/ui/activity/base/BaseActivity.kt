package {{.answers.appPackageName}}.ui.activity.base

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.crashlytics.android.Crashlytics
import {{.answers.appPackageName}}.R
import {{.answers.appPackageName}}.helpers.KeyboardHelper


open class BaseActivity : MvpAppCompatActivity() {
	
	//region ==================== Lifecycle callbacks ====================
	
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
	
	//region ==================== Protected ====================
	
	protected fun showSnackBar(message: String, actionText: String? = null, action: View.OnClickListener? = null, container: View? = null) {
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
	
	protected fun initToolbar(@StringRes titleResId: Int) {
		initToolbar(getString(titleResId))
	}
	
	protected fun initToolbar(title: String) {
		/*val toolbar = findViewById<Toolbar>(R.id.toolbar)
		toolbar?.let {
			it.title = title
		}
		setSupportActionBar(toolbar)*/
	}
	
	//endregion
	
}