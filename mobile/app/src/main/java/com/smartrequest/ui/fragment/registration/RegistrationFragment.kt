package com.smartrequest.ui.fragment.registration

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.extensions.appspecific.configurePhoneNumberFormatter
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.other.TextWatcherAdapter
import com.smartrequest.ui.other.showErrorMessage
import com.smartrequest.ui.other.span.ClickSpan
import com.smartrequest.ui.widget.TitledInput
import io.mobilife.upscreenmessage.UpScreenMessage
import javax.inject.Inject
import javax.inject.Provider


class RegistrationFragment : BaseFragment(), RegistrationContract.View, FragmentWithPreloader {

	@InjectPresenter
	lateinit var presenter: RegistrationContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<RegistrationContract.Presenter>

	lateinit var contentContainer: ViewGroup
	lateinit var tiName: TitledInput
	lateinit var tiEmail: TitledInput
	lateinit var tiAddress: TitledInput
	lateinit var tiPhoneNumber: TitledInput
	lateinit var tiPassword: TitledInput
	lateinit var btnRegister: View
	lateinit var authButton: TextView

	//region ==================== Fragment creation ====================

	companion object {

		fun newInstance(): RegistrationFragment {
			return RegistrationFragment()
		}
	}

	//endregion

	//region ==================== Lifecycle ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_registration, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== UI handlers ====================

	private val btnCloseClickListener =
			View.OnClickListener { presenter.onCloseButtonClickListener() }

	private val keyboardDoneButtonClickListener =
			TextView.OnEditorActionListener { _, actionId, _ ->
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					presenter.onRegistrationButtonClicked(tiName.text, tiAddress.text, tiEmail.text, tiPhoneNumber.text, tiPassword.text)
					return@OnEditorActionListener true
				}
				return@OnEditorActionListener false
			}

	private val textWatcher = object : TextWatcherAdapter() {
		override fun afterTextChanged(editable: Editable) {
			presenter.onInputValuesChanged(tiName.text, tiAddress.text, tiEmail.text, tiPhoneNumber.text, tiPassword.text)
		}
	}

	//endregion

	//region ==================== RegistrationContract.View ====================

	override fun setAddress(text: String?) {
		tiAddress.text = text ?: ""
	}

	override fun setRegistrationButtonEnabled(enabled: Boolean) {
		btnRegister.isEnabled = enabled
	}

	override fun showErrorMessage(message: String) {
		UpScreenMessage.showErrorMessage(message, contentContainer)
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(RegistrationModule())
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): RegistrationContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		setupToolbar(view, R.string.screen_title_registration, null, true, btnCloseClickListener)

		contentContainer = view.findViewById(R.id.content_container)
		tiName = view.findViewById(R.id.ti_name)
		tiName.editText.addTextChangedListener(textWatcher)

		tiEmail = view.findViewById(R.id.ti_email)
		tiEmail.editText.addTextChangedListener(textWatcher)

		tiAddress = view.findViewById(R.id.ti_address)
		tiAddress.setOnClickListener { presenter.onAddressClicked() }

		tiPhoneNumber = view.findViewById(R.id.ti_phone_number)
		tiPhoneNumber.editText.configurePhoneNumberFormatter()
		tiPhoneNumber.editText.addTextChangedListener(textWatcher)

		tiPassword = view.findViewById(R.id.ti_password)
		tiPassword.editText.setOnEditorActionListener(keyboardDoneButtonClickListener)
		tiPassword.editText.addTextChangedListener(textWatcher)

		btnRegister = view.findViewById(R.id.btn_registration)
		btnRegister.setOnClickListener {
			presenter.onRegistrationButtonClicked(tiName.text, tiAddress.text,
					tiEmail.text, tiPhoneNumber.text, tiPassword.text)
		}

		presenter.onInputValuesChanged(tiName.text, tiAddress.text, tiEmail.text, tiPhoneNumber.text, tiPassword.text)

		authButton = view.findViewById(R.id.btn_authorization)
		ClickSpan.clickify(authButton, resources.getString(R.string.registration_screen_login_link_part), false) {
			presenter.onAuthorizationButtonClicked()
		}

	}

	//endregion


}