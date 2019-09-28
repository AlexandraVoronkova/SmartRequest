package com.smartrequest.ui.fragment.registration

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.data.entities.auth.AuthService
import com.smartrequest.extensions.appspecific.getErrorMessage
import com.smartrequest.helpers.FormatHelper
import com.smartrequest.ui.navigation.AppScreenContainerParams
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.ui.other.resources.ResourceProvider
import com.smartrequest.utils.RxUtils
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RegistrationPresenter @Inject constructor(private val router: Router,
                                                private val authService: AuthService) :
        RegistrationContract.Presenter() {

    //region ==================== MVP Presenter ====================

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    //endregion

    //region ==================== RegistrationContract.Presenter ====================

    override fun onAddressClicked() {
        router.setResultListener(Screens.ResultCodes.RESULT_CODE_ADDRESS) { data ->
            router.removeResultListener(Screens.ResultCodes.RESULT_CODE_ADDRESS)
            (data as? String)?.let {
                viewState.setAddress(data)
            }
        }

        router.navigateTo(Screens.ADDRESS_LIST_SCREEN)
    }

    override fun onCloseButtonClickListener() {
        router.exit()
    }

    override fun onInputValuesChanged(name: String, address: String, email: String, phoneNumber: String,
                                      password: String) {
        viewState.setRegistrationButtonEnabled(isInputValuesValid(name, address, email, phoneNumber, password))
    }

    override fun onRegistrationButtonClicked(name: String, address: String, email: String, phoneNumber: String,
                                             password: String) {
        if (!isInputValuesValid(name, address, email, phoneNumber, password)) {
            return
        }
        viewState.showPreloader()
        val disposable = authService.register(name, address, email, phoneNumber, password)
                .compose(RxUtils.applyCompletableSchedulers())
                .subscribe({
                    viewState.hidePreloader()
                    router.newRootScreen(Screens.MAIN_SCREEN_CONTAINER)
                }, {
                    viewState.hidePreloader()
                    it.printStackTrace()
                    it.message?.let {
                        viewState?.showErrorMessage(it)
                    }
                })
        compositeDisposable.add(disposable)


    }

    override fun onAuthorizationButtonClicked() {
        router.replaceScreen(Screens.LOGIN_SCREEN)
    }

    //endregion

    //region ===================== Internal ======================

    private fun isInputValuesValid(name: String, address: String, email: String, phoneNumber: String,
                                   password: String): Boolean {
        return name.isNotBlank() && address.isNotBlank() &&
                FormatHelper.isValidEmail(email) && FormatHelper.isValidPhoneNumber(phoneNumber) &&
                password.isNotBlank()
    }

    //endregion


}