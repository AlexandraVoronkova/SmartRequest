package com.smartrequest.ui.fragment.login

import android.os.Bundle
import android.text.Editable
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.other.TextWatcherAdapter
import com.smartrequest.ui.other.showErrorMessage
import com.smartrequest.ui.other.showSuccessMessage
import com.smartrequest.ui.other.span.ClickSpan
import com.smartrequest.ui.widget.TitledInput
import io.mobilife.upscreenmessage.UpScreenMessage
import javax.inject.Inject
import javax.inject.Provider


class LoginFragment : BaseFragment(), LoginContract.View, FragmentWithPreloader {

	@InjectPresenter
	lateinit var presenter: LoginContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<LoginContract.Presenter>

	lateinit var contentContainer: ViewGroup
	lateinit var tiEmail: TitledInput
	lateinit var tiPassword: TitledInput
	lateinit var btnLogin: View
	lateinit var btnForgotPassword: View


	//region ==================== Fragment creation ====================

	companion object {

		private const val EXTRA_KEY_MESSAGE = "EXTRA_KEY_MESSAGE"

		fun newInstance(): LoginFragment {
			return LoginFragment()
		}
	}

	//endregion

	//region ==================== Lifecycle ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_login, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== UI handlers ====================

	private val btnCloseClickListener = View.OnClickListener { presenter.onCloseButtonClicked() }

	private val textWatcher = object: TextWatcherAdapter() {
		override fun afterTextChanged(editable: Editable) {
			presenter.onInputValuesChanged(tiEmail.text, tiPassword.text)
		}
	}

	private val keyboardDoneButtonClickListener = TextView.OnEditorActionListener { _, actionId, _ ->
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			presenter.onAuthorizationButtonClicked(tiEmail.text, tiPassword.text)
			return@OnEditorActionListener true
		}
		return@OnEditorActionListener false
	}

	//endregion

	//region ==================== LoginContract.View ====================

	override fun showInitialMessageIfExists() {
		val message: String? = arguments?.getString(EXTRA_KEY_MESSAGE)
		if (message != null) {
			UpScreenMessage.showSuccessMessage(message, contentContainer)
		}
	}

	override fun setLoginButtonEnabled(enabled: Boolean) {
		btnLogin.isEnabled = enabled
	}

	override fun showErrorMessage(message: String) {
		UpScreenMessage.showErrorMessage(message, contentContainer)
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(LoginModule())
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): LoginContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		setupToolbar(view, R.string.screen_title_authorization, null, false, btnCloseClickListener)

		contentContainer = view.findViewById(R.id.content_container)
		tiEmail = view.findViewById(R.id.ti_email)
		tiEmail.editText.addTextChangedListener(textWatcher)

		tiPassword = view.findViewById(R.id.ti_password)
		tiPassword.editText.transformationMethod = PasswordTransformationMethod()
		tiPassword.editText.addTextChangedListener(textWatcher)
		tiPassword.editText.setOnEditorActionListener(keyboardDoneButtonClickListener)

		btnLogin = view.findViewById(R.id.btn_authorization)
		btnLogin.setOnClickListener { presenter.onAuthorizationButtonClicked(tiEmail.text, tiPassword.text) }
		presenter.onInputValuesChanged(tiEmail.text, tiPassword.text)

		val btnRegistration: TextView = view.findViewById(R.id.btn_registration)
		ClickSpan.clickify(btnRegistration, resources.getString(R.string.login_screen_register_link_part), false) {
			presenter.onRegistrationButtonClicked()
		}

	}

	//endregion


}