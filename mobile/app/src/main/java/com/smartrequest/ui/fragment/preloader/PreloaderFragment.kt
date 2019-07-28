package com.smartrequest.ui.fragment.preloader

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.smartrequest.R
import com.smartrequest.ui.fragment.base.BaseDialogFragment
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider


class PreloaderFragment : BaseDialogFragment(), PreloaderContract.View {

	@InjectPresenter
	lateinit var presenter: PreloaderContract.Presenter

	@Inject
	lateinit var presenterProvider: Provider<PreloaderContract.Presenter>

	private lateinit var progressIndicator: ProgressBar

	private var mStartTime: Long = -1L

	private var mPostedHide = false

	private var mPostedShow = false

	private var mDismissed = false

	private val mDelayedHide = Runnable {
		mPostedHide = false
		mStartTime = -1
		val parentFragmentManager = this.parentFragmentManager
		(parentFragmentManager?.findFragmentByTag(FRAGMENT_TAG) as? PreloaderFragment)?.let {
			it.dismissAllowingStateLoss()
			parentFragmentManager.executePendingTransactions()
		}
	}

	private val mDelayedShow = Runnable {
		mPostedShow = false
		if (!mDismissed) {
			mStartTime = System.currentTimeMillis()
			view?.visibility = View.VISIBLE
		}
	}

	private var parentFragmentManager: FragmentManager? = null


	//region ==================== Fragment creation and static methods ====================

	companion object {

		private const val MIN_SHOW_TIME = 500L // ms
		private const val MIN_DELAY = 500L // ms

		private const val FRAGMENT_TAG = "preloader_fragment_tag"

		fun newInstance(): PreloaderFragment {
			val args = Bundle()

			val fragment = PreloaderFragment()
			fragment.arguments = args

			return fragment
		}

		fun show(fragmentManager: FragmentManager) {
			val preloaderFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG) as? PreloaderFragment
			if (preloaderFragment == null) {
				PreloaderFragment.newInstance().show(fragmentManager, FRAGMENT_TAG)
				fragmentManager.executePendingTransactions()
			} else {
				Timber.w("Preloader fragment already exist!")
			}
		}

		fun hide(fragmentManager: FragmentManager) {
			(fragmentManager.findFragmentByTag(FRAGMENT_TAG) as? PreloaderFragment).let {
				it?.remove(fragmentManager)
			}
		}
	}

	//endregion

	//region ==================== Lifecycle ====================

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		removeCallbacks()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_preloader, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
		show()
	}

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val dialog = super.onCreateDialog(savedInstanceState)

		val window = dialog.window
		window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
		isCancelable = false
		return dialog
	}

	override fun onDetach() {
		super.onDetach()
		removeCallbacks()
	}

	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(PreloaderModule())
		component.inject(this)
	}

	@ProvidePresenter
	internal fun providePresenter(): PreloaderContract.Presenter {
		return presenterProvider.get()
	}

	//endregion

	//region ==================== UI ====================

	private fun initUI(view: View) {
		progressIndicator = view.findViewById(R.id.progress_indicator)
	}

	private fun removeCallbacks() {
		view?.removeCallbacks(mDelayedHide)
		view?.removeCallbacks(mDelayedShow)
		this.parentFragmentManager = null
	}

	/**
	 * Removes the progress fragment if it is visible. The progress fragment will not be
	 * removed until it has been shown for at least a minimum show time. If the
	 * progress fragment was not yet visible, cancels showing the progress fragment.
	 */
	public fun remove(parentFragmentManager: FragmentManager? = null) {
		mDismissed = true
		view?.removeCallbacks(mDelayedShow)
		this.parentFragmentManager = null
		val diff = System.currentTimeMillis() - mStartTime
		if (diff >= MIN_SHOW_TIME || mStartTime == -1L) {
			// The progress view has been shown long enough
			// OR was not shown yet. If it wasn't shown yet,
			// it will just never be shown.
			(parentFragmentManager?.findFragmentByTag(FRAGMENT_TAG) as? PreloaderFragment)?.let {
				it.dismissAllowingStateLoss()
				parentFragmentManager.executePendingTransactions()
			}
		} else {
			// The progress fragment is shown, but not long enough,
			// so put a delayed message in to remove it when its been
			// shown long enough.
			if (!mPostedHide) {
				this.parentFragmentManager = parentFragmentManager
				view?.postDelayed(mDelayedHide, MIN_SHOW_TIME - diff)
				mPostedHide = true
			}
		}
	}

	/**
	 * Show the progress fragment after waiting for a minimum delay. If
	 * during that time, remove() is called, the fragment is never made visible.
	 */
	private fun show() {
		// Reset the start time.
		mStartTime = -1
		mDismissed = false
		view?.removeCallbacks(mDelayedHide)
		if (!mPostedShow) {
			view?.postDelayed(mDelayedShow, MIN_DELAY)
			mPostedShow = true
		}
	}

	//endregion


}