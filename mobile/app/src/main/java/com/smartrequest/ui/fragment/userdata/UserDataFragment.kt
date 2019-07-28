package com.smartrequest.ui.fragment.userdata

import android.os.Bundle
import android.text.Editable
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R;
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.other.TextWatcherAdapter
import com.smartrequest.ui.other.showErrorMessage
import com.smartrequest.ui.other.showSuccessMessage
import com.smartrequest.ui.widget.UnderlinedTitledInput
import io.mobilife.upscreenmessage.UpScreenMessage
import javax.inject.Inject
import javax.inject.Provider

class UserDataFragment : BaseFragment(), UserDataContract.View, FragmentWithPreloader {

    @InjectPresenter
    lateinit var presenter: UserDataContract.Presenter

    @Inject
    lateinit var presenterProvider: Provider<UserDataContract.Presenter>

    lateinit var utiName: UnderlinedTitledInput
    lateinit var utiEmail: UnderlinedTitledInput
    lateinit var utiPhoneNumber: UnderlinedTitledInput
    lateinit var utiAddress: UnderlinedTitledInput
    lateinit var btnLogout: View

    lateinit var contentContainer: ViewGroup

    //region ==================== Fragment creation ====================

    companion object {
        fun newInstance(): UserDataFragment {
            val args = Bundle()

            val fragment = UserDataFragment()
            fragment.arguments = args

            return fragment
        }
    }

    //endregion

    //region ==================== Lifecycle ====================

    override fun onCreate(savedInstanceState: Bundle?) {
        configureDI()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_data_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
    }

    //endregion

    //region ==================== Options menu ====================

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_user_data_screen_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                presenter.onSaveButtonClicked()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    //endregion

    //region ==================== UI handlers ====================

    //endregion

    //region ==================== EditParentProfileContract.View ====================

    override fun setName(text: String?) {
        utiName.text = text ?: ""
    }

    override fun setEmail(text: String?) {
        utiEmail.text = text ?: ""
    }

    override fun setPhoneNumber(text: String?) {
        utiPhoneNumber.clearTextChangedListeners()
        utiPhoneNumber.text = text ?: ""
        utiPhoneNumber.configurePhoneNumberFormatter()
    }

    override fun setAddress(text: String?) {
        utiAddress.text = text ?: ""
    }

    override fun requestTextInputValues() {
        presenter.handleTextInputValues(utiName.text, utiAddress.text, utiEmail.text, utiPhoneNumber.text)
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
        val component = getAppComponent().plus(UserDataModule())
        component.inject(this)
    }

    @ProvidePresenter
    internal fun providePresenter(): UserDataContract.Presenter {
        return presenterProvider.get()
    }

    //endregion

    //region ==================== UI ====================

    private fun initUI(view: View) {
        setupToolbar(view, R.string.screen_title_edit_user_data, null, false, null)
        contentContainer = view.findViewById(R.id.content_container)

        utiName = view.findViewById(R.id.uti_name)
        utiAddress = view.findViewById(R.id.uti_address)
        utiAddress.setOnClickListener { presenter.onAddressClicked() }
        utiEmail = view.findViewById(R.id.uti_email)
        utiPhoneNumber = view.findViewById(R.id.uti_phone_number)
        utiPhoneNumber.configurePhoneNumberFormatter()
        btnLogout = view.findViewById(R.id.btn_logout)
        btnLogout.setOnClickListener { presenter.onLogoutButtonClicked() }
    }

    //endregion


}