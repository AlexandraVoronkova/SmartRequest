package com.smartrequest.ui.activity.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.activity.base.BaseActivity
import com.smartrequest.ui.navigation.ApplicationNavigator
import com.smartrequest.ui.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import org.parceler.Parcels
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider


class AuthScreenActivity : BaseActivity(), AuthScreenContract.View {

	companion object {

		const val EXTRA_START_SCREEN = "EXTRA_START_SCREEN"
		const val EXTRA_START_SCREEN_DATA = "EXTRA_START_SCREEN_DATA"

	}

	@InjectPresenter
	lateinit var presenter: AuthScreenContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<AuthScreenContract.Presenter>

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

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val startScreen = intent.getStringExtra(EXTRA_START_SCREEN) ?: Screens.LOGIN_SCREEN

		val startScreenData: Any? = Parcels.unwrap(intent.getParcelableExtra(EXTRA_START_SCREEN_DATA))
		val component = getAppComponent().plus(AuthScreenModule(startScreen, startScreenData));
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): AuthScreenContract.Presenter {
		return presenterProvider.get()
	}

	override fun shouldMonitorAuthStatus(): Boolean {
		return false
	}

	//endregion

	//region ==================== AuthScreenContract.View ====================

	//endregion

	//region ===================== Internal ======================


	//endregion

}