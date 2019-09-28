package com.smartrequest.ui.fragment.timepicker

import android.app.TimePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {

	//region ==================== Fragment callbacks ====================

	interface TimePickerFragmentListener {
		fun onTimeSet(date: Date, extraData: Bundle?)
	}

	//endregion

	companion object {

		val KEY_EXTRA_TIME = "KEY_EXTRA_DATE"
		val KEY_EXTRA_PAYLOAD = "KEY_EXTRA_PAYLOAD"
		val KEY_EXTRA_TIMEZONE = "KEY_EXTRA_TIMEZONE"

		//endregion

		//region ===================== New Instance ======================

		fun newInstance(date: Date?, extraData: Bundle?, timeZone: String? = null): TimePickerFragment {
			val fragment = TimePickerFragment()
			val args = Bundle()

			if (date != null) {
				args.putSerializable(KEY_EXTRA_TIME, date)
			}
			if (extraData != null) {
				args.putBundle(KEY_EXTRA_PAYLOAD, extraData)
			}
			if (timeZone != null) {
				args.putString(KEY_EXTRA_TIMEZONE, timeZone)
			}

			fragment.arguments = args
			return fragment
		}

		//endregion
	}


	//region ==================== Callbacks ====================

	private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
		try {
			val extraData = arguments!!.getBundle(KEY_EXTRA_PAYLOAD)
			val timezone = arguments!!.getString(KEY_EXTRA_TIMEZONE, null)
			val calendar = Calendar.getInstance()
			calendar.clear()
			if (timezone != null) {
				calendar.timeZone = TimeZone.getTimeZone(timezone)
			}
			calendar.set(Calendar.HOUR_OF_DAY, hour)
			calendar.set(Calendar.MINUTE, minute)
			val date = calendar.time
			if (parentFragment != null && parentFragment is TimePickerFragmentListener) {
				(parentFragment as TimePickerFragmentListener).onTimeSet(date, extraData)
			} else if (activity != null && activity is TimePickerFragmentListener) {
				(activity as TimePickerFragmentListener).onTimeSet(date, extraData)
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	//region ==================== Lifecycle ====================

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val arguments = arguments
		val date = arguments!!.getSerializable(KEY_EXTRA_TIME) as? Date
		val calendar = Calendar.getInstance()
		if (date != null) {
			calendar.time = date
		}
		val timezone = arguments.getString(KEY_EXTRA_TIMEZONE, null)
		if (timezone != null) {
			calendar.timeZone = TimeZone.getTimeZone(timezone)
		}
		val hour = calendar.get(Calendar.HOUR_OF_DAY)
		val minute = calendar.get(Calendar.MINUTE)
		val timePickerDialog = TimePickerDialog(activity!!, timeSetListener, hour, minute, true)

		return timePickerDialog
	}

	//endregion


}