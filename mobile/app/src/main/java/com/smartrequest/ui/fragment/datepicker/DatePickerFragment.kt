package com.smartrequest.ui.fragment.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {

	//region ==================== Fragment callbacks ====================

	interface DatePickerFragmentListener {
		fun onDateSet(date: Date, extraData: Bundle?)
	}

	//endregion

	companion object {

		val KEY_EXTRA_DATE = "KEY_EXTRA_DATE"
		val KEY_EXTRA_MIN_DATE = "KEY_EXTRA_MIN_DATE"
		val KEY_EXTRA_MAX_DATE = "KEY_EXTRA_MAX_DATE"
		val KEY_EXTRA_PAYLOAD = "KEY_EXTRA_PAYLOAD"

		//endregion

		//region ===================== New Instance ======================

		fun newInstance(date: Date?, extraData: Bundle?): DatePickerFragment {
			return newInstance(date, null, null, extraData)
		}

		fun newInstance(date: Date?,
		                minDate: Date?,
		                maxDate: Date?,
		                extraData: Bundle?): DatePickerFragment {
			val fragment = DatePickerFragment()
			val args = Bundle()

			if (date != null) {
				args.putSerializable(KEY_EXTRA_DATE, date)
			}
			if (minDate != null) {
				args.putSerializable(KEY_EXTRA_MIN_DATE, minDate)
			}
			if (maxDate != null) {
				args.putSerializable(KEY_EXTRA_MAX_DATE, maxDate)
			}
			if (extraData != null) {
				args.putBundle(KEY_EXTRA_PAYLOAD, extraData)
			}

			fragment.arguments = args
			return fragment
		}

		//endregion
	}


	//region ==================== Callbacks ====================

	private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
		try {
			val extraData = arguments!!.getBundle(KEY_EXTRA_PAYLOAD)
			val calendar = Calendar.getInstance()
			calendar.clear()
			calendar.set(Calendar.YEAR, year)
			calendar.set(Calendar.MONTH, month)
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
			val date = calendar.time
			if (parentFragment != null && parentFragment is DatePickerFragmentListener) {
				(parentFragment as DatePickerFragmentListener).onDateSet(date, extraData)
			} else if (activity != null && activity is DatePickerFragmentListener) {
				(activity as DatePickerFragmentListener).onDateSet(date, extraData)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	//region ==================== Lifecycle ====================

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val arguments = arguments
		val date = arguments!!.getSerializable(KEY_EXTRA_DATE) as? Date
		val calendar = Calendar.getInstance()
		if (date != null) {
			calendar.time = date
		}
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		val day = calendar.get(Calendar.DAY_OF_MONTH)
		val datePickerDialog = DatePickerDialog(activity!!, dateSetListener, year, month, day)

		if (arguments.containsKey(KEY_EXTRA_MIN_DATE) || arguments.containsKey(KEY_EXTRA_MAX_DATE)) {
			val datePicker = datePickerDialog.datePicker
			if (arguments.containsKey(KEY_EXTRA_MIN_DATE)) {
				val minDate = (arguments.getSerializable(KEY_EXTRA_MIN_DATE) as Date).time
				datePicker.minDate = minDate
			}

			if (arguments.containsKey(KEY_EXTRA_MAX_DATE)) {
				val maxDate = (arguments.getSerializable(KEY_EXTRA_MAX_DATE) as Date).time
				datePicker.maxDate = maxDate
			}
		}

		return datePickerDialog
	}

	//endregion


}