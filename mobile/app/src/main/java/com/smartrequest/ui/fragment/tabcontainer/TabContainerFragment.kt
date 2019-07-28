package com.smartrequest.ui.fragment.tabcontainer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartrequest.R
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.navigation.ApplicationNavigator
import com.smartrequest.ui.navigation.BackButtonListener
import com.smartrequest.ui.navigation.LocalCiceroneHolder
import com.smartrequest.ui.navigation.RouterProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class TabContainerFragment : BaseFragment(), RouterProvider, BackButtonListener {

	private var navigator: Navigator? = null

	@Inject
	lateinit var ciceroneHolder: LocalCiceroneHolder

	private val containerName: String?
		get() = arguments!!.getString(EXTRA_NAME)

	val cicerone: Cicerone<Router>
		get() = ciceroneHolder.getCicerone(containerName!!)

	companion object {
		private val EXTRA_NAME = "extra_name"
		private val START_SCREEN = "start_screen"

		fun newInstance(name: String, startScreen: String): TabContainerFragment {
			val fragment = TabContainerFragment()

			fragment.arguments = Bundle().apply {
				putString(EXTRA_NAME, name)
				putString(START_SCREEN, startScreen)
			}

			return fragment
		}
	}

	//region ==================== Lifecycle callbacks ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_tab_container, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		if (childFragmentManager.findFragmentById(R.id.tab_container) == null) {
			cicerone.router.replaceScreen(arguments!!.getString(START_SCREEN))
		}
	}

	override fun onResume() {
		super.onResume()
		cicerone.navigatorHolder.setNavigator(getNavigator())
	}

	override fun onPause() {
		cicerone.navigatorHolder.removeNavigator()
		super.onPause()
	}

	//endregion

	//region ==================== Navigation ====================

	private fun getNavigator(): Navigator {
		if (navigator == null) {
			navigator = ApplicationNavigator(activity!!, childFragmentManager, R.id.tab_container)
		}
		return navigator!!
	}

	override fun getRouter(): Router {
		return cicerone.router
	}

	override fun onBackPressed(): Boolean {
		val fragment = childFragmentManager.findFragmentById(R.id.tab_container)
		if (fragment != null
				&& fragment is BackButtonListener
				&& (fragment as BackButtonListener).onBackPressed()) {
			return true
		} else {
			if (childFragmentManager.backStackEntryCount > 0) {
				getRouter().exit()
				return true
			} else if (activity is RouterProvider) {
				(activity as RouterProvider).getRouter().exit()
				return true
			}
		}
		return false
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		getAppComponent().inject(this)
	}

	//endregion
}
