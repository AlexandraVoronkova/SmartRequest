package com.smartrequest.ui.fragment.addresslist

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.data.entities.address.AddressService
import com.smartrequest.data.entities.auth.AuthService
import com.smartrequest.data.entities.category.CategoryService
import com.smartrequest.data.entities.category.model.Category
import com.smartrequest.extensions.appspecific.getErrorMessage
import com.smartrequest.helpers.FormatHelper
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.fragment.addresslist.adapter.AddressItemViewModel
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
class AddressListPresenter @Inject constructor(private val router: Router,
											   private val addressService: AddressService) : AddressListContract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
	}

	//endregion

	//region ==================== AddressListContract.Presenter ====================

	override fun onAddressChange(address: String) {
		val disposable = addressService.getAddressList(address)
				.compose(RxUtils.applySingleSchedulers())
				.subscribe({
					showItemList(it.map { it.value })
				}, {
					it.printStackTrace()
					it.message?.let {
						viewState?.showErrorMessage(it)
					}
				})
		compositeDisposable.add(disposable)
	}

	override fun onBackButtonClicked() {
		router.exit()
	}

	override fun onItemClicked(item: ListViewModel) {
		when (item) {
			is AddressItemViewModel -> {
				router.exitWithResult(Screens.ResultCodes.RESULT_CODE_ADDRESS, item.name)
			}
		}
	}

	//endregion

	//region ===================== Internal ======================

	private fun showItemList(address: List<String>) {
		val viewModels = ArrayList<ListViewModel>()

		address.forEach {
			viewModels.add(AddressItemViewModel(it, it))
		}

		viewState.showItemlist(viewModels)
	}

	//endregion


}