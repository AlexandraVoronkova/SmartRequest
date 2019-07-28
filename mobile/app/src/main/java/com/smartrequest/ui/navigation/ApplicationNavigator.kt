package com.smartrequest.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.smartrequest.ui.activity.auth.AuthScreenActivity
import com.smartrequest.ui.activity.authoptions.AuthOptionsScreenActivity
import com.smartrequest.ui.activity.container.ContainerScreenActivity
import com.smartrequest.ui.activity.main.MainActivity
import com.smartrequest.ui.activity.main.model.MainScreenParams
import com.smartrequest.ui.activity.translucentcontainer.TranslucentContainerScreenActivity
import com.smartrequest.ui.fragment.addresslist.AddressListFragment
import com.smartrequest.ui.fragment.authoptions.AuthOptionsFragment
import com.smartrequest.ui.fragment.call.CallFragment
import com.smartrequest.ui.fragment.categorylist.CategoryListFragment
import com.smartrequest.ui.fragment.inputtext.InputTextFragment
import com.smartrequest.ui.fragment.login.LoginFragment
import com.smartrequest.ui.fragment.registration.RegistrationFragment
import com.smartrequest.ui.fragment.requestlist.RequestListFragment
import com.smartrequest.ui.fragment.stub.StubFragment
import com.smartrequest.ui.fragment.userdata.UserDataFragment
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Forward


open class ApplicationNavigator(private val activity: FragmentActivity,
                                protected val fragmentManager: FragmentManager,
                                containerId: Int) :
		SupportAppNavigator(activity, fragmentManager, containerId) {

	constructor(activity: FragmentActivity,
	            containerId: Int) : this(activity, activity.supportFragmentManager, containerId)

	override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
		when (screenKey) {
			Screens.AUTH_OPTIONS_SCREEN_CONTAINER -> {
				val intent = Intent(context, AuthOptionsScreenActivity::class.java)
				intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
				return intent
			}
			Screens.AUTH_SCREEN_CONTAINER -> {

				if (data !is AppScreenContainerParams) {
					throw IllegalArgumentException("Invalid params for ${Screens.AUTH_SCREEN_CONTAINER}")
				}
				val intent = Intent(context, AuthScreenActivity::class.java)
				intent.putExtra(ContainerScreenActivity.EXTRA_START_SCREEN, data.screen)
				data.data?.let {
					intent.putExtra(AuthScreenActivity.EXTRA_START_SCREEN_DATA, it)
				}
				return intent
			}
			Screens.WEB_BROWSER -> {
				val url = data as? String
						?: throw IllegalArgumentException("Wrong data for ${Screens.WEB_BROWSER}: " + data.toString())

				val browserIntent = Intent(Intent.ACTION_VIEW)
				browserIntent.data = Uri.parse(url)
				return Intent.createChooser(browserIntent, null)
			}
			Screens.MAIN_SCREEN_CONTAINER -> {
				val intent = Intent(context, MainActivity::class.java)
				if (data != null && data is MainScreenParams) {
					intent.putExtra(MainActivity.EXTRA_START_SCREEN, data.selectedTabScreen)
					intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
				} else {
					intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
				}
				return intent
			}
			Screens.APP_SCREEN_CONTAINER -> {
				if (data !is AppScreenContainerParams) {
					throw IllegalArgumentException("Invalid params for ${Screens.APP_SCREEN_CONTAINER}")
				}
				val intent = Intent(context, ContainerScreenActivity::class.java)
				intent.putExtra(ContainerScreenActivity.EXTRA_START_SCREEN, data.screen)
				data.data?.let {
					intent.putExtra(ContainerScreenActivity.EXTRA_START_SCREEN_DATA, it)
				}
				return intent
			}
			Screens.TRANSLUCENT_APP_SCREEN_CONTAINER -> {
				if (data !is AppScreenContainerParams) {
					throw IllegalArgumentException("Invalid params for ${Screens.TRANSLUCENT_APP_SCREEN_CONTAINER}")
				}
				val intent = Intent(context, TranslucentContainerScreenActivity::class.java)
				intent.putExtra(ContainerScreenActivity.EXTRA_START_SCREEN, data.screen)
				data.data?.let {
					intent.putExtra(ContainerScreenActivity.EXTRA_START_SCREEN_DATA, it)
				}
				return intent
			}
			Screens.PHONE_CALL_SCREEN -> {
				if (data !is String) {
					throw IllegalArgumentException("Invalid params for ${Screens.PHONE_CALL_SCREEN}")
				}
				val intent = Intent(Intent.ACTION_DIAL)
				intent.data = Uri.parse("tel:$data")
				return intent
			}
			Screens.SMS_SCREEN -> {
				if (data !is String) {
					throw IllegalArgumentException("Invalid params for ${Screens.SMS_SCREEN}")
				}
				val uri = Uri.parse("smsto:")
				val intent = Intent(Intent.ACTION_SENDTO, uri)
				intent.putExtra("sms_body", data)
				return intent
			}
		}

		return null
	}

	override fun createFragment(screenKey: String, data: Any?): Fragment? {
		when (screenKey) {
			Screens.AUTH_OPTIONS_SCREEN -> {
				return AuthOptionsFragment.newInstance()
			}
			Screens.LOGIN_SCREEN ->
				return LoginFragment.newInstance()
			Screens.REGISTRATION_SCREEN -> {
				return RegistrationFragment.newInstance()
			}
			Screens.USER_DATA -> {
				return UserDataFragment.newInstance()
			}
			Screens.CALL_SCREEN -> {
				return CallFragment.newInstance()
			}
			Screens.CATEGORY_SCREEN -> {
				return CategoryListFragment.newInstance()
			}
			Screens.INPUT_SCREEN -> {
				return InputTextFragment.newInstance()
			}
			Screens.ADDRESS_LIST_SCREEN -> {
				return AddressListFragment.newInstance()
			}
			Screens.REQUEST_LIST_SCREEN -> {
				return RequestListFragment.newInstance()
			}
			Screens.STUB_SCREEN ->
				return StubFragment.newInstance()
		}

		return null
	}

	override fun forward(command: Forward) {
		when (command.screenKey) {
			in listOf<String>() -> {
				val fragment =
						createFragment(command.screenKey, command.transitionData) as DialogFragment
				val currentFragment: DialogFragment? =
						fragmentManager.findFragmentByTag(command.screenKey) as? DialogFragment
				currentFragment?.dismissAllowingStateLoss()
				fragmentManager.executePendingTransactions()
				val transaction = fragmentManager.beginTransaction()
				transaction.addToBackStack(command.screenKey)
				fragment.show(transaction, command.screenKey)
				localStackCopy.add(command.screenKey)
			}
			else -> super.forward(command)
		}
	}


}