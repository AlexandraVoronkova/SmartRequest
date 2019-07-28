package {{.answers.appPackageName}}.helpers

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

object KeyboardHelper {
	
	fun hideSoftKeyboard(activity: Activity?) {
		if (activity == null) {
			return
		}
		
		if (activity.currentFocus != null) {
			val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
			inputManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken,
					InputMethodManager.HIDE_NOT_ALWAYS)
		} else {
			activity.window.setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
		}
	}
	
}