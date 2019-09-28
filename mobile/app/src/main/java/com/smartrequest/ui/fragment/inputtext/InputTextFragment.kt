package com.smartrequest.ui.fragment.inputtext

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.other.showErrorMessage
import io.mobilife.upscreenmessage.UpScreenMessage
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject
import javax.inject.Provider

class InputTextFragment : BaseFragment(), InputTextContract.View {

	@InjectPresenter
	lateinit var presenter: InputTextContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<InputTextContract.Presenter>

	private lateinit var etMessage: EditText
	private lateinit var fabSendMessage: View

	private lateinit var upScreenMessageContainer: ViewGroup

	//region ==================== Fragment creation ====================

	companion object {

		const val GALLERY_PICKER_CODE = 100
		const val CAMERA_PICKER_CODE = 101

		const val EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 110
		const val CAMERA_PERMISSION_REQUEST_CODE = 111

		fun newInstance(): InputTextFragment {

			val fragment = InputTextFragment()
			return fragment

		}
	}

	//endregion

	//region ==================== Lifecycle ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_input_text, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initUI(view)
	}

	//endregion

	//region ==================== UI handlers ====================

	private val btnBackClickListener = View.OnClickListener { presenter.onBackButtonClicked() }

	//endregion

	//region ==================== FeedbackContract.View ====================

	override fun showErrorMessage(message: String) {
		UpScreenMessage.showErrorMessage(message, upScreenMessageContainer)
	}

	//endregion

	//region ==================== EasyPermissions.PermissionCallbacks ====================

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(InputTextModule())
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): InputTextContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		setupToolbar(view, R.string.screen_title_description,
				null, true, btnBackClickListener)

		upScreenMessageContainer = view.findViewById(R.id.up_screen_message_container)

		etMessage = view.findViewById(R.id.tv_feedback_message)

		fabSendMessage = view.findViewById(R.id.fab_send_feedback)
		fabSendMessage.setOnClickListener {
			presenter.onSendMessageButtonClicked(etMessage.text.toString())
		}

	}

	//endregion

}