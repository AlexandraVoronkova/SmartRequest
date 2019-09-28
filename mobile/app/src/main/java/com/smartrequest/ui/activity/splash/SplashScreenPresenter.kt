package com.smartrequest.ui.activity.splash

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.ui.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SplashScreenPresenter @Inject constructor(private val router: Router,
                                                private val authService: com.smartrequest.data.entities.auth.AuthService) :
        SplashScreenContract.Presenter() {

    companion object {
        @JvmStatic
        var demoScreenAlreadyHasBeenShown = false
    }

    //region ==================== MVP Presenter ====================

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    //endregion

    //region ==================== SplashScreenContract.Presenter ====================

    override fun onScreenLoadedWithoutExtraParams() {
        checkAuthStatusAndPerformActionIfAuthorized {
            router.newRootScreen(Screens.MAIN_SCREEN_CONTAINER)
        }
    }

    override fun onExtraParamsReceived(data: Map<String, Any>) {
        checkAuthStatusAndPerformActionIfAuthorized {
            router.newRootScreen(Screens.MAIN_SCREEN_CONTAINER)
        }
    }

    //endregion

    //region ===================== Internal ======================

    private fun checkAuthStatusAndPerformActionIfAuthorized(action: () -> Unit) {
        if (authService.isLoggedIn()) {
            action.invoke()
        } else {
            router.newRootScreen(Screens.AUTH_OPTIONS_SCREEN_CONTAINER)
        }

    }

    //endregion
}