package com.smartrequest.ui.activity.authoptions

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.activity.base.BaseActivity
import com.smartrequest.ui.navigation.ApplicationNavigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject
import javax.inject.Provider


class AuthOptionsScreenActivity : BaseActivity(), AuthOptionsScreenContract.View {

	companion object {

		const val KEY_DISPLAY_MODE = "KEY_DISPLAY_MODE"

	}

	@InjectPresenter
	lateinit var presenter: AuthOptionsScreenContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<AuthOptionsScreenContract.Presenter>

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
		val component = getAppComponent().plus(AuthOptionsScreenModule())
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): AuthOptionsScreenContract.Presenter {
		return presenterProvider.get()
	}

	override fun shouldMonitorAuthStatus(): Boolean {
		return false
	}

	//endregion

	//region ==================== AuthOptionsScreenContract.View ====================

	//endregion

}