package com.smartrequest.ui.fragment.requestlist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.adapter.listener.ListItemClickListener
import com.smartrequest.ui.fragment.addresslist.adapter.AddressListAdapter
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.fragment.requestlist.adapter.RequestListAdapter
import com.smartrequest.ui.other.RxSearchObservable
import com.smartrequest.ui.other.showErrorMessage
import com.smartrequest.ui.other.showSuccessMessage
import com.smartrequest.utils.RxUtils
import io.mobilife.upscreenmessage.UpScreenMessage
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class RequestListFragment : BaseFragment(), RequestListContract.View, FragmentWithPreloader {

	@InjectPresenter
	lateinit var presenter: RequestListContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<RequestListContract.Presenter>

	lateinit var contentContainer: ViewGroup

	@Inject
	lateinit var adapter: RequestListAdapter

	private lateinit var rvRequest: RecyclerView
	private lateinit var tvEmpty: View


	//region ==================== Fragment creation ====================

	companion object {

		fun newInstance(): RequestListFragment {
			return RequestListFragment()
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
		return inflater.inflate(R.layout.fragment_request_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== Options menu ====================

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.add_request_screen_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.action_add -> {
				presenter.onAddRequestClicked()
				true
			}
			else ->
				super.onOptionsItemSelected(item)
		}
	}

	//endregion

	//region ==================== UI handlers ====================

	private val itemClickListener = ListItemClickListener { presenter.onItemClicked(it) }

	//endregion

	//region ==================== LoginContract.View ====================

	override fun setEmptyState(isVisible: Boolean) {
		tvEmpty.visibility = if (isVisible) {
			VISIBLE
		} else {
			GONE
		}
	}

	override fun showItemlist(items: List<ListViewModel>) {
		adapter.swapItems(items)
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
		val component = getAppComponent().plus(RequestListModule(activity!!, itemClickListener))
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): RequestListContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		setupToolbar(view, R.string.screen_title_request_list, null, false, null)
		rvRequest = view.findViewById(R.id.rv_request)
		rvRequest.layoutManager = LinearLayoutManager(context!!, VERTICAL, false)
		rvRequest.adapter = adapter

		tvEmpty = view.findViewById(R.id.tv_empty_text)

		contentContainer = view.findViewById(R.id.content_container)

	}

	//endregion


}