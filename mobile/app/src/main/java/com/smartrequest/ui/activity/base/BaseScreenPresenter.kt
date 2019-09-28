package com.smartrequest.ui.activity.base

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.utils.RxUtils
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

@InjectViewState
class BaseScreenPresenter @Inject constructor(private val router: Router,
                                              private val authService: com.smartrequest.data.entities.auth.AuthService,
                                              @Named("monitor_auth_status") private val shouldMonitorAuthStatus: Boolean) : BaseScreenContract.Presenter() {

	private var authStatusDisposable: Disposable? = null

	//region ==================== MVP Presenter ====================

	override fun attachView(view: BaseScreenContract.View?) {
		super.attachView(view)
		if (shouldMonitorAuthStatus) {
			authStatusDisposable = authService.getAuthStatusObservable()
					.compose(RxUtils.applySchedulers())
					.filter { it == false }
					.take(1)
					.subscribe({ isAuthorized ->
						if (!isAuthorized) {
							router.newRootScreen(Screens.AUTH_OPTIONS_SCREEN_CONTAINER)
						}
					}, {
						it.printStackTrace()
					})
			compositeDisposable.add(authStatusDisposable!!)
		}
	}

	override fun detachView(view: BaseScreenContract.View?) {
		super.detachView(view)
		authStatusDisposable?.dispose()
	}

	//endregion

	//region ===================== BaseScreenContract.Presenter ======================


	//endregion

}