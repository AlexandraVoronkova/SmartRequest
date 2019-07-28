package com.smartrequest.ui.activity.main

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.activity.base.BaseActivity
import com.smartrequest.ui.fragment.tabcontainer.TabContainerFragment
import com.smartrequest.ui.navigation.ApplicationNavigator
import com.smartrequest.ui.navigation.BackButtonListener
import com.smartrequest.ui.navigation.RouterProvider
import com.smartrequest.ui.navigation.Screens
import pub.devrel.easypermissions.EasyPermissions
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.util.*
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : BaseActivity(), MainScreenContract.View, RouterProvider {

	@InjectPresenter
	lateinit var presenter: MainScreenContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<MainScreenContract.Presenter>

	@Inject
	lateinit var navigatorHolder: NavigatorHolder

	@Inject
	lateinit var appRouter: Router

	private val bottomNavigationView: BottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation_view) }
	private lateinit var unreadNotificationsIndicator: View
	private lateinit var unreadMessagesIndicator: View

	private lateinit var userDataTabFragment: TabContainerFragment
	private lateinit var requestListTabFragment: TabContainerFragment

	companion object {

		const val EXTRA_START_SCREEN = "EXTRA_START_SCREEN"

		private const val TAB_USER_DATA = "TAB_USER_DATA"
		private const val TAB_REQUEST_LIST = "TAB_REQUEST_LIST"

		private const val LOCATION_PERMISSION_REQUEST = 300

	}


	//region ==================== Lifecycle callbacks ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initUI()
	}

	override fun onResumeFragments() {
		super.onResumeFragments()
		navigatorHolder.setNavigator(navigator)
	}

	override fun onPause() {
		navigatorHolder.removeNavigator()
		super.onPause()
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)
	}

	//endregion

	//region ==================== UI handlers ====================

	private val onNavigationItemSelectedListener =
			BottomNavigationView.OnNavigationItemSelectedListener { item ->
				when (item.itemId) {
					R.id.navigation_user_data -> {
						presenter.onUserDataNavigationItemClicked()
						return@OnNavigationItemSelectedListener true
					}
					R.id.navigation_call -> {
						presenter.onCallNavigationItemClicked()
						return@OnNavigationItemSelectedListener true
					}
				}
				return@OnNavigationItemSelectedListener false
			}

	override fun onBackPressed() {
		val fragment = supportFragmentManager.findFragmentById(R.id.tabs_container)
		if (fragment != null
				&& fragment is BackButtonListener
				&& (fragment as BackButtonListener).onBackPressed()) {
			return
		} else {
			presenter.onBackPressed()
		}
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(MainScreenModule());
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): MainScreenContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== MainScreenContract.View ====================

	override fun requestLocationPermissionsIfNotGranted() {
		if (!EasyPermissions.hasPermissions(this, ACCESS_FINE_LOCATION)) {
			EasyPermissions.requestPermissions(
					this,
					getString(R.string.geolocation_permission_request_rationale),
					LOCATION_PERMISSION_REQUEST,
					ACCESS_FINE_LOCATION)
		}
	}

	override fun setUserHaveUnreadNotifications(haveUnreadNotifications: Boolean) {
		if (haveUnreadNotifications) {
			unreadNotificationsIndicator.visibility = View.VISIBLE
		} else {
			unreadNotificationsIndicator.visibility = View.INVISIBLE
		}
	}

	override fun setUserHaveUnreadMessages(haveUnreadMessages: Boolean) {
		if (haveUnreadMessages) {
			unreadMessagesIndicator.visibility = View.VISIBLE
		} else {
			unreadMessagesIndicator.visibility = View.INVISIBLE
		}
	}

	//endregion

	//region ==================== EasyPermissions ====================

	override fun onRequestPermissionsResult(requestCode: Int,
	                                        permissions: Array<String>,
	                                        grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		// EasyPermissions handles the request result.
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
	}

	//endregion

	//region ==================== RouterProvider ====================

	override fun getRouter(): Router {
		return appRouter
	}

	//endregion

	//region ==================== Navigation ====================

	private val navigator = object : ApplicationNavigator(this, -1) {

		override fun applyCommands(commands: Array<Command>) {
			fragmentManager.executePendingTransactions()
			copyStackToLocal()

			for (command in commands) {
				applyCommand(command)
			}
		}

		private fun copyStackToLocal() {
			localStackCopy = LinkedList()

			val stackSize = fragmentManager.backStackEntryCount
			for (i in 0 until stackSize) {
				localStackCopy.add(fragmentManager.getBackStackEntryAt(i).name)
			}
		}

		override fun applyCommand(command: Command) {
			if (command is Replace) {
				val fm = supportFragmentManager

				when (command.screenKey) {
					Screens.BOTTOM_TAB_USER_DATA_SCREEN -> {
						activateTab(fm, userDataTabFragment,
								listOf(requestListTabFragment),
								R.id.navigation_user_data)
					}
					Screens.BOTTOM_TAB_CALL_SCREEN -> {
						activateTab(fm, requestListTabFragment,
								listOf(userDataTabFragment),
								R.id.navigation_call)
					}
					else -> super.replace(command)
				}
			} else {
				super.applyCommand(command)
			}
		}
	}

	private fun activateTab(fm: FragmentManager, activeFragment: Fragment,
	                        inactiveFragments: List<Fragment>, @IdRes selectedItemId: Int) {
		val transaction = fm.beginTransaction()
		for (fragment in inactiveFragments) {
			transaction.detach(fragment)
		}
		transaction.attach(activeFragment)
		transaction.commitNow()
		bottomNavigationView.setOnNavigationItemSelectedListener(null)
		bottomNavigationView.selectedItemId = selectedItemId
		bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI() {
		bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
		bottomNavigationView.disableShiftMode()

		initContainers()
	}

	private fun initContainers() {
		val fm = supportFragmentManager

		this.userDataTabFragment = getInitializedTabContainerFragment(TAB_USER_DATA, Screens.USER_DATA)
		this.requestListTabFragment = getInitializedTabContainerFragment(TAB_REQUEST_LIST, Screens.REQUEST_LIST_SCREEN)

	}

	private fun getInitializedTabContainerFragment(tag: String,
	                                               startScreen: String): TabContainerFragment {
		val fm = supportFragmentManager
		var fragment = fm.findFragmentByTag(tag) as? TabContainerFragment
		if (fragment == null) {
			fragment = TabContainerFragment.newInstance(tag, startScreen)
			fm.beginTransaction()
					.add(R.id.tabs_container, fragment, tag)
					.detach(fragment).commitNow()
		}
		return fragment
	}

	private fun BottomNavigationView.disableShiftMode() {
		try {
			val menuView = getChildAt(0) as? BottomNavigationMenuView
			menuView?.javaClass?.getDeclaredField("mShiftingMode")?.apply {
				isAccessible = true
				setBoolean(menuView, false)
				isAccessible = false
			}

			menuView?.let {
				@SuppressLint("RestrictedApi")
				for (i in 0 until menuView.childCount) {
					(menuView.getChildAt(i) as BottomNavigationItemView).apply {
						setShifting(false)
						setChecked(false)
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}

	}

	//endregion

	//region ===================== Internal ======================


	//endregion

}