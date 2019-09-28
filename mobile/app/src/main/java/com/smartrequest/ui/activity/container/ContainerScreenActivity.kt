package com.smartrequest.ui.activity.container

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.terrakok.cicerone.NavigatorHolder
import com.smartrequest.R
import com.smartrequest.ui.activity.base.BaseActivity
import com.smartrequest.ui.navigation.ApplicationNavigator
import com.smartrequest.ui.navigation.BackButtonListener
import org.parceler.Parcels
import javax.inject.Inject
import javax.inject.Provider


open class ContainerScreenActivity : BaseActivity(), ContainerScreenContract.View {

	companion object {

		const val EXTRA_START_SCREEN = "EXTRA_START_SCREEN"
		const val EXTRA_START_SCREEN_DATA = "EXTRA_START_SCREEN_DATA"

	}

	@InjectPresenter
	lateinit var presenter: ContainerScreenContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<ContainerScreenContract.Presenter>

	@Inject
	lateinit var navigatorHolder: NavigatorHolder

	private val navigator = ApplicationNavigator(this, R.id.content_container)


	//region ==================== Lifecycle callbacks ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_fragment_container)

	}

	override fun onResumeFragments() {
		super.onResumeFragments()
		navigatorHolder.setNavigator(navigator)
	}

	override fun onPause() {
		navigatorHolder.removeNavigator()
		super.onPause()
	}

	override fun onBackPressed() {
		val fragment = supportFragmentManager.findFragmentById(R.id.content_container)
		if (fragment != null
				&& fragment is BackButtonListener
				&& (fragment as BackButtonListener).onBackPressed()) {
			return
		} else {
			super.onBackPressed()
		}
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val startScreen = intent.getStringExtra(EXTRA_START_SCREEN) ?: null
		val startScreenData: Any? = Parcels.unwrap(intent.getParcelableExtra(EXTRA_START_SCREEN_DATA))
		val component = getAppComponent().plus(ContainerScreenModule(startScreen, startScreenData));
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): ContainerScreenContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== ContainerScreenContract.View ====================

	//endregion

}