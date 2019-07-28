package com.smartrequest.ui.fragment.categorylist

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.data.entities.auth.AuthService
import com.smartrequest.data.entities.category.CategoryService
import com.smartrequest.data.entities.category.model.Category
import com.smartrequest.extensions.appspecific.getErrorMessage
import com.smartrequest.helpers.FormatHelper
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.fragment.categorylist.adapter.CategoryItemViewModel
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.ui.other.resources.ResourceProvider
import com.smartrequest.utils.RxUtils
import io.reactivex.Completable
import io.reactivex.Observable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class CategoryListPresenter @Inject constructor(private val router: Router,
												private val categoryService: CategoryService) : CategoryListContract.Presenter() {

	private var categoryTree = emptyList<Pair<Category, List<Category>>>()

	private var selectedCategoryId: Int? = null

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
		val disposable = categoryService.getCategoryList()
				.flatMapObservable { Observable.fromIterable(it) }
				.flatMapSingle { parent -> categoryService.getCategoryListById(parent.id).map { return@map Pair(parent, it) } }
				.toList()
				.compose(RxUtils.applySingleSchedulers())
				.subscribe({
					categoryTree = it
					showItemList()
				}, { throwable ->
					viewState.hidePreloader()
					throwable.printStackTrace()
					throwable.getErrorMessage()?.let {
						viewState.showErrorMessage(it)
					}
				})
		compositeDisposable.add(disposable)
	}

	//endregion

	//region ==================== CategoryListContract.Presenter ====================

	override fun onBackButtonClicked() {
		router.exit()
	}

	override fun onItemClicked(item: ListViewModel) {
		when (item) {
			is CategoryItemViewModel -> {
				if (!item.isParent) {
					router.exitWithResult(Screens.ResultCodes.RESULT_CODE_CATEGORY, Category(item.listItemId!!.toInt(), item.name))
				} else {
					selectedCategoryId = item.listItemId!!.toInt()
					showItemList()
				}
			}
		}
	}

	//endregion

	//region ===================== Internal ======================

	private fun showItemList() {
		val viewModels = ArrayList<ListViewModel>()

		categoryTree.forEach {
			viewModels.add(CategoryItemViewModel(it.first.id.toString(), it.first.name, true))
			if (it.first.id == selectedCategoryId) {
				it.second.forEach {
					viewModels.add(CategoryItemViewModel(it.id.toString(), it.name, false))
				}
			}
		}

		viewState.showItemlist(viewModels)
	}

	//endregion


}