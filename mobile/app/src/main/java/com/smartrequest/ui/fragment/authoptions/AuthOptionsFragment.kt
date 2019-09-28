package com.smartrequest.ui.fragment.authoptions

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.fragment.base.BaseFragment
import javax.inject.Inject
import javax.inject.Provider

class AuthOptionsFragment : BaseFragment(), AuthOptionsContract.View {

	@InjectPresenter
	lateinit var presenter: AuthOptionsContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<AuthOptionsContract.Presenter>

	private lateinit var btnRegister: View
	private lateinit var btnLogin: View

	//region ==================== Fragment creation ====================

	companion object {

		fun newInstance(): AuthOptionsFragment {

			return AuthOptionsFragment()
		}
	}

	//endregion

	//region ==================== Lifecycle ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_auth_options, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== UI handlers ====================

	private val btnRegisterClickListener = View.OnClickListener { presenter.onRegisterButtonClicked() }

	private val btnLoginClickListener = View.OnClickListener { presenter.onLoginButtonClicked() }

	//endregion

	//region ==================== AuthOptionsContract.View ====================

	override fun showMessage(message: String) {
		showSnackBar(message)
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(AuthOptionsModule())
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): AuthOptionsContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		btnRegister = view.findViewById<View>(R.id.btn_registration)
		btnRegister.setOnClickListener(btnRegisterClickListener)

		btnLogin = view.findViewById<View>(R.id.btn_authorization)
		btnLogin.setOnClickListener(btnLoginClickListener)

	}

	//endregion


}