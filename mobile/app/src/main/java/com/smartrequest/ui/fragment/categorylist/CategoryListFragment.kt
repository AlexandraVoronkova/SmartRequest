package com.smartrequest.ui.fragment.categorylist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
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
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.adapter.listener.ListItemClickListener
import com.smartrequest.ui.fragment.base.BaseFragment
import com.smartrequest.ui.fragment.base.view.FragmentWithPreloader
import com.smartrequest.ui.fragment.categorylist.adapter.CategoryListAdapter
import com.smartrequest.ui.other.TextWatcherAdapter
import com.smartrequest.ui.other.showErrorMessage
import com.smartrequest.ui.other.showSuccessMessage
import com.smartrequest.ui.other.span.ClickSpan
import com.smartrequest.ui.widget.TitledInput
import io.mobilife.upscreenmessage.UpScreenMessage
import javax.inject.Inject
import javax.inject.Provider


class CategoryListFragment : BaseFragment(), CategoryListContract.View, FragmentWithPreloader {

	@InjectPresenter
	lateinit var presenter: CategoryListContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<CategoryListContract.Presenter>

	lateinit var contentContainer: ViewGroup

	@Inject
	lateinit var adapter: CategoryListAdapter

	private lateinit var rvHelp: RecyclerView



	//region ==================== Fragment creation ====================

	companion object {

		fun newInstance(): CategoryListFragment {
			return CategoryListFragment()
		}
	}

	//endregion

	//region ==================== Lifecycle ====================

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_category_list, container, false)
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
		val component = getAppComponent().plus( CategoryListModule(activity!!, itemClickListener))
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): CategoryListContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		setupToolbar(view, R.string.screen_title_category_list,  null, true, btnBackClickListener)
		rvHelp = view.findViewById(R.id.rv_categories)
		rvHelp.layoutManager = LinearLayoutManager(context!!, VERTICAL, false)
		rvHelp.adapter = adapter
	}

	//endregion


}