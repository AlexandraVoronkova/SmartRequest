package com.smartrequest.ui.fragment.confirm

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.smartrequest.R


class ConfirmDialogFragment : DialogFragment() {

	companion object {

		@JvmField
		val KEY_MESSAGE = "message"
		@JvmField
		val KEY_POSITIVE_BUTTON_TITLE = "positiveButtonTitle"
		@JvmField
		val KEY_NEGATIVE_BUTTON_TITLE = "negativeButtonTitle"
		@JvmField
		val KEY_EXTRAS = "extras"

		fun newInstance(message: String, confirmButtonTitle: String? = null, cancelButtonTitle: String? = null, extras: Bundle? = null): ConfirmDialogFragment {
			val fragment = ConfirmDialogFragment()

			val arguments = Bundle()
			arguments.putString(KEY_MESSAGE, message)
			arguments.putString(KEY_POSITIVE_BUTTON_TITLE, confirmButtonTitle)
			arguments.putString(KEY_NEGATIVE_BUTTON_TITLE, cancelButtonTitle)
			arguments.putBundle(KEY_EXTRAS, extras)
			fragment.arguments = arguments

			return fragment
		}

	}

	interface ConfirmDialogFragmentCallbacks {

		fun onPositiveButtonClicked(extras: Bundle?) {
		}

		fun onNegativeButtonClicked(extras: Bundle?) {
		}

	}

	//region ==================== Lifecycle callbacks ====================

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val alertDialogBuilder = AlertDialog.Builder(requireActivity())
		val message = arguments?.getString(KEY_MESSAGE)
		alertDialogBuilder.setMessage(message)

		val okButtonTitle = arguments?.getString(KEY_POSITIVE_BUTTON_TITLE, getString(R.string.common_button_ok))
		val cancelButtonTitle = arguments?.getString(KEY_NEGATIVE_BUTTON_TITLE, getString(R.string.common_button_cancel))

		alertDialogBuilder.setPositiveButton(okButtonTitle) { dialog, which ->
			dialog.dismiss()
			parentFragment?.let {
				if (it is ConfirmDialogFragmentCallbacks) {
					it.onPositiveButtonClicked(arguments?.getBundle(KEY_EXTRAS))
				}
			}
			if (parentFragment == null) {
				if (activity is ConfirmDialogFragmentCallbacks) {
					(activity as ConfirmDialogFragmentCallbacks).onPositiveButtonClicked(arguments?.getBundle(KEY_EXTRAS))
				}
			}
		}
		alertDialogBuilder.setNegativeButton(cancelButtonTitle) { dialog, which ->
			dialog.dismiss()
			parentFragment?.let {
				if (it is ConfirmDialogFragmentCallbacks) {
					it.onNegativeButtonClicked(arguments?.getBundle(KEY_EXTRAS))
				}
			}
			if (parentFragment == null) {
				if (activity is ConfirmDialogFragmentCallbacks) {
					(activity as ConfirmDialogFragmentCallbacks).onNegativeButtonClicked(arguments?.getBundle(KEY_EXTRAS))
				}
			}
		}

		return alertDialogBuilder.create()
	}

	//endregion

}