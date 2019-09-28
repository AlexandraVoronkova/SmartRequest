package com.smartrequest.ui.fragment.requestlist

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.data.entities.address.AddressService
import com.smartrequest.data.entities.auth.AuthService
import com.smartrequest.data.entities.category.CategoryService
import com.smartrequest.data.entities.category.model.Category
import com.smartrequest.data.entities.request.RequestService
import com.smartrequest.data.entities.request.model.Request
import com.smartrequest.extensions.appspecific.getErrorMessage
import com.smartrequest.helpers.FormatHelper
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.fragment.addresslist.adapter.AddressItemViewModel
import com.smartrequest.ui.fragment.categorylist.adapter.CategoryItemViewModel
import com.smartrequest.ui.fragment.requestlist.adapter.RequestViewModel
import com.smartrequest.ui.navigation.AppScreenContainerParams
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.ui.other.resources.ResourceProvider
import com.smartrequest.utils.RxUtils
import io.reactivex.Completable
import io.reactivex.Observable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class RequestListPresenter @Inject constructor(private val router: Router,
											   private val requestService: RequestService) : RequestListContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()

		loadRequests()
	}

	//endregion

	//region ==================== RequestListContract.Presenter ====================

	override fun onItemClicked(item: ListViewModel) {
		when (item) {

		}
	}

	override fun onAddRequestClicked() {
		router.setResultListener(Screens.ResultCodes.RESULT_CODE_REQUEST) { data ->
			router.removeResultListener(Screens.ResultCodes.RESULT_CODE_REQUEST)
			(data as? Boolean)?.let {
				viewState.showSuccessMessage("Заявка успешно создана")
				loadRequests()
			}
		}

		router.navigateTo(Screens.APP_SCREEN_CONTAINER,
				AppScreenContainerParams(Screens.CALL_SCREEN))
	}

	//endregion

	//region ===================== Internal ======================

	private fun showItemList(requestList: List<Request>) {
		val viewModels = ArrayList<ListViewModel>()

		requestList.forEach {
			viewModels.add(RequestViewModel(it.id.toString(), it.name, it.status))
		}

		viewState.showItemlist(viewModels)
	}

	private fun loadRequests() {
		val disposable = requestService.getStatusList()
				.compose(RxUtils.applySingleSchedulers())
				.subscribe({
					viewState.setEmptyState(it.isEmpty())
					showItemList(it)
				}, { throwable ->
					throwable.printStackTrace()
					throwable.getErrorMessage()?.let {
						viewState.showErrorMessage(it)
					}
				})
		compositeDisposable.add(disposable)
	}

	//endregion


}