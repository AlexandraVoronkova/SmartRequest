package {{.answers.appPackageName}}.ui.fragment.base

import android.support.annotation.StringRes
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import {{.answers.appPackageName}}.helpers.KeyboardHelper

open class BaseFragment : MvpAppCompatFragment() {
	
	//region ==================== Lifecycle ====================
	
	override fun onPause() {
		super.onPause()
		KeyboardHelper.hideSoftKeyboard(activity)
	}
	
	//endregion
	
	//region ==================== Toast ====================
	
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