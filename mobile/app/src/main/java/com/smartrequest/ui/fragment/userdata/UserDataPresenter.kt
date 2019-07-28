package com.smartrequest.ui.fragment.userdata

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.R
import com.smartrequest.data.entities.address.AddressService
import com.smartrequest.data.entities.auth.AuthService
import com.smartrequest.data.entities.user.model.UserData
import com.smartrequest.helpers.FormatHelper
import com.smartrequest.ui.navigation.AppScreenContainerParams
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.ui.other.resources.ResourceProvider
import com.smartrequest.utils.RxUtils
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@InjectViewState
class UserDataPresenter @Inject constructor(private val router: Router,
											private val resourceProvider: ResourceProvider,
											private val authService: AuthService,
											private val addressService: AddressService,
											private val userDataService: com.smartrequest.data.entities.user.UserDataService) : UserDataContract.Presenter() {

	private var user: UserData? = null

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()

		val user = userDataService.getUser()

		if (user != null) {
			onUserLoaded(user)
		}
	}

	//endregion

	//region ==================== EditParentProfileContract.Presenter ====================

	override fun onSaveButtonClicked() {
		viewState.requestTextInputValues()
	}

	override fun onAddressClicked() {
		router.setResultListener(Screens.ResultCodes.RESULT_CODE_ADDRESS) { data ->
			router.removeResultListener(Screens.ResultCodes.RESULT_CODE_ADDRESS)
			(data as? String)?.let {
				viewState.setAddress(data)
			}
		}

		router.navigateTo(Screens.APP_SCREEN_CONTAINER,
				AppScreenContainerParams(Screens.ADDRESS_LIST_SCREEN))
	}

	override fun onLogoutButtonClicked() {
		val disposable = authService.logout()
				.compose(RxUtils.applyCompletableSchedulers())
				.subscribe({ }, {
					it.printStackTrace()
				})
		compositeDisposable.add(disposable)
	}

	override fun handleTextInputValues(name: String, address: String, email: String, phoneNumber: String) {
		if (name.isBlank()) {
			viewState.showErrorMessage(resourceProvider.getString(R.string.edit_parent_profile_screen_error_name_not_specified))
			return
		} else if (!FormatHelper.isValidPhoneNumber(phoneNumber)) {
			viewState.showErrorMessage(resourceProvider.getString(R.string.edit_parent_profile_screen_error_phone_number_not_specified))
			return
		} else if (address.isBlank()) {
			viewState.showErrorMessage(resourceProvider.getString(R.string.edit_parent_profile_screen_error_address_not_specified))
			return
		} else if (email.isBlank()) {
			viewState.showErrorMessage(resourceProvider.getString(R.string.edit_parent_profile_screen_error_email_not_specified))
			return
		}

		userDataService.saveUser(UserData(Random().nextLong().toString(), name, address, email, phoneNumber))
		viewState?.showSuccessMessage(resourceProvider.getString(R.string.edit_parent_profile_screen_success_message))
	}

	//endregion

	//region ==================== Internal ====================

	private fun onUserLoaded(user: UserData) {
		this.user = user
		viewState.setName(user.fio)

		viewState.setPhoneNumber(user.phoneNumber)

		viewState.setAddress(user.address)

		viewState.setEmail(user.email)
	}

	//endregion


}