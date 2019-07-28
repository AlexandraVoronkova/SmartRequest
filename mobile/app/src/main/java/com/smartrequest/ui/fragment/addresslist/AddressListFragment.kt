package com.smartrequest.ui.fragment.addresslist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.adapter.listener.ListItemClickListener
import com.smartrequest.ui.fragment.addresslist.adapter.AddressListAdapter
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.other.RxSearchObservable
import com.smartrequest.ui.other.showErrorMessage
import com.smartrequest.utils.RxUtils
import io.mobilife.upscreenmessage.UpScreenMessage
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class AddressListFragment : BaseFragment(), AddressListContract.View, FragmentWithPreloader {

	@InjectPresenter
	lateinit var presenter: AddressListContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<AddressListContract.Presenter>

	lateinit var contentContainer: ViewGroup

	@Inject
	lateinit var adapter: AddressListAdapter

	private lateinit var rvAddress: RecyclerView
	private lateinit var etAddress: EditText


	//region ==================== Fragment creation ====================

	companion object {

		fun newInstance(): AddressListFragment {
			return AddressListFragment()
		}
	}

	//endregion

	//region ==================== Lifecycle ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_address_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== UI handlers ====================

	private val btnBackClickListener = View.OnClickListener { presenter.onBackButtonClicked() }

	private val itemClickListener = ListItemClickListener { presenter.onItemClicked(it) }

	//endregion

	//region ==================== LoginContract.View ====================

	override fun showItemlist(items: List<ListViewModel>) {
		adapter.swapItems(items)
	}

	override fun showErrorMessage(message: String) {
		UpScreenMessage.showErrorMessage(message, contentContainer)
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(AddressListModule(activity!!, itemClickListener))
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): AddressListContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		setupToolbar(view, R.string.screen_title_address_list, R.drawable.ic_action_close, true, btnBackClickListener)
		rvAddress = view.findViewById(R.id.rv_address)
		rvAddress.layoutManager = LinearLayoutManager(context!!, VERTICAL, false)
		rvAddress.adapter = adapter

		etAddress = view.findViewById(R.id.et_address)
		observeEditText()
	}

	private fun observeEditText() {
		RxSearchObservable.fromView(etAddress)
				.debounce(500, TimeUnit.MILLISECONDS)
				.distinctUntilChanged()
				.compose(RxUtils.applySchedulers())
				.subscribe {
					presenter.onAddressChange(it)
				}
	}

	//endregion


}