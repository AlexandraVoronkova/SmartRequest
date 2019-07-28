package com.smartrequest.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.terrakok.cicerone.NavigatorHolder
import com.smartrequest.R
import com.smartrequest.ui.activity.base.BaseActivity
import com.smartrequest.ui.navigation.ApplicationNavigator
import javax.inject.Inject
import javax.inject.Provider


class SplashScreenActivity : BaseActivity(), SplashScreenContract.View {

	@InjectPresenter
	lateinit var presenter: SplashScreenContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<SplashScreenContract.Presenter>

	@Inject
	lateinit var navigatorHolder: NavigatorHolder

	private val navigator = ApplicationNavigator(this, -1)


	//region ==================== Lifecycle callbacks ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash_screen)
		presenter.onScreenLoadedWithoutExtraParams()
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
		val component = getAppComponent().plus(SplashScreenModule());
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): SplashScreenContract.Presenter {
		return presenterProvider.get()
	}

	override fun shouldMonitorAuthStatus(): Boolean {
		return false
	}

	//endregion

	//region ==================== SplashScreenContract.View ====================

	//endregion

	//region ===================== Internal ======================

	//endregion

}