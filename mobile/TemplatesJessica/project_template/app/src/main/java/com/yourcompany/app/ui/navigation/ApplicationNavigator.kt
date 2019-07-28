package {{.answers.appPackageName}}.ui.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import ru.terrakok.cicerone.android.SupportAppNavigator
import java.util.*


open class ApplicationNavigator(private val activity: FragmentActivity,
                                protected val fragmentManager: FragmentManager,
                                containerId: Int) :
		SupportAppNavigator(activity, fragmentManager, containerId) {

	constructor(activity: FragmentActivity, containerId: Int) : this(activity, activity.supportFragmentManager, containerId)

	override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
		when (screenKey) {

		}

		return null
	}

	override fun createFragment(screenKey: String, data: Any?): Fragment? {
		when (screenKey) {

		}

		return null
	}
}