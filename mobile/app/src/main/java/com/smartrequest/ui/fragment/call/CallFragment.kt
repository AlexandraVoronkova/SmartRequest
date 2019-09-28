package com.smartrequest.ui.fragment.call

import android.os.Bundle
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R;
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.fragment.datepicker.DatePickerFragment
import com.smartrequest.ui.other.showErrorMessage
import com.smartrequest.ui.other.showSuccessMessage
import com.smartrequest.ui.widget.UnderlinedTitledInput
import com.smartrequest.ui.widget.VerticallyTitledValue
import io.mobilife.upscreenmessage.UpScreenMessage
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class CallFragment : BaseFragment(), CallContract.View, FragmentWithPreloader {

	@InjectPresenter
	lateinit var presenter: CallContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<CallContract.Presenter>

	lateinit var contentContainer: ViewGroup

	lateinit var vtvCategory: VerticallyTitledValue
	lateinit var vtvComment: VerticallyTitledValue
	lateinit var btnSend: View


	//region ==================== Fragment creation ====================

	companion object {
		fun newInstance(): CallFragment {
			val args = Bundle()

			val fragment = CallFragment()
			fragment.arguments = args

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
		return inflater.inflate(R.layout.fragment_call, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== UI handlers ====================

	private val btnBackClickListener = View.OnClickListener { presenter.onBackButtonClicked() }

	//endregion

	//region ==================== EditParentProfileContract.View ====================

	override fun setCategory(category: String) {
		vtvCategory.text = category
	}

	override fun setComment(comment: String) {
		vtvComment.text = comment
	}

	override fun showSuccessMessage(message: String) {
		UpScreenMessage.showSuccessMessage(message, contentContainer)
	}

	override fun showErrorMessage(message: String) {
		UpScreenMessage.showErrorMessage(message, contentContainer)
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(CallModule())
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): CallContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		setupToolbar(view, R.string.screen_title_call, null, true, btnBackClickListener)
		contentContainer = view.findViewById(R.id.content_container)

		vtvCategory = view.findViewById(R.id.vtv_category)
		vtvCategory.setOnClickListener { presenter.onCategoryButtonClicked() }
		vtvComment = view.findViewById(R.id.vtv_comment)
		vtvComment.setOnClickListener { presenter.onCommentButtonClicked("") }
		btnSend = view.findViewById(R.id.btn_send)
		btnSend.setOnClickListener { presenter.onSendButtonClicked() }
	}

	//endregion


}