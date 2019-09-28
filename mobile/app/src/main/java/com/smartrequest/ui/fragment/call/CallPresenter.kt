package com.smartrequest.ui.fragment.call

import com.arellomobile.mvp.InjectViewState
import com.smartrequest.R
import com.smartrequest.data.entities.auth.AuthService
import com.smartrequest.data.entities.category.model.Category
import com.smartrequest.data.entities.request.RequestService
import com.smartrequest.data.entities.request.model.Request
import com.smartrequest.data.entities.user.model.UserData
import com.smartrequest.extensions.appspecific.getErrorMessage
import com.smartrequest.helpers.FormatHelper
import com.smartrequest.ui.navigation.AppScreenContainerParams
import com.smartrequest.ui.navigation.Screens
import com.smartrequest.ui.other.resources.ResourceProvider
import com.smartrequest.utils.RxUtils
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@InjectViewState
class CallPresenter @Inject constructor(private val router: Router,
										private val resourceProvider: ResourceProvider,
										private val requestService: RequestService) : CallContract.Presenter() {

	private var selectedCategory: Category? = null
	private var comment: String? = null

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()

		setInfo()
	}

	//endregion

	//region ==================== CallContract.Presenter ====================

	override fun onSendButtonClicked() {
		if (comment.isNullOrBlank()) {
			viewState.showErrorMessage(resourceProvider.getString(R.string.call_screen_empty_comment))
			return
		} else if (selectedCategory == null) {
			viewState.showErrorMessage(resourceProvider.getString(R.string.call_screen_empty_category))
			return
		} else {
			val disposable = requestService.sendRequest(1, selectedCategory!!.id, comment!!)
					.compose(RxUtils.applyCompletableSchedulers())
					.subscribe({
						requestService.addRequest(Request(1, selectedCategory!!.name, "На рассмотрении"))
						router.exitWithResult(Screens.ResultCodes.RESULT_CODE_REQUEST, true)
					}, { throwable ->
						viewState.hidePreloader()
						throwable.printStackTrace()
						throwable.getErrorMessage()?.let {
							viewState.showErrorMessage(it)
						}
					})
			compositeDisposable.add(disposable)
		}

	}

	override fun onCategoryButtonClicked() {
		router.setResultListener(Screens.ResultCodes.RESULT_CODE_CATEGORY) { data ->
			router.removeResultListener(Screens.ResultCodes.RESULT_CODE_CATEGORY)
			(data as? Category)?.let {
				selectedCategory = it
				viewState.setCategory(it.name)
				setInfo()
			}
		}

		router.navigateTo(Screens.APP_SCREEN_CONTAINER,
				AppScreenContainerParams(Screens.CATEGORY_SCREEN))
	}

	override fun onCommentButtonClicked(comment: String) {
		router.setResultListener(Screens.ResultCodes.RESULT_CODE_TEXT) { data ->
			router.removeResultListener(Screens.ResultCodes.RESULT_CODE_TEXT)
			(data as? String)?.let {
				this.comment = it
				viewState.setComment(it)
				setInfo()
			}
		}

		router.navigateTo(Screens.APP_SCREEN_CONTAINER,
				AppScreenContainerParams(Screens.INPUT_SCREEN))
	}

	override fun onBackButtonClicked() {
		router.exit()
	}

	//endregion

	//region ==================== Internal ====================

	private fun setInfo() {
		val defaultString = resourceProvider.getString(R.string.common_unknown)

		viewState.setCategory(selectedCategory?.name ?: defaultString)
		viewState.setComment(comment ?: defaultString)

	}

	//endregion


}