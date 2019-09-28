package com.smartrequest.extensions.shared

import java.util.*
import kotlin.math.abs

fun Date.startOfDay(timezone: TimeZone? = null): Date {
	val calendar = Calendar.getInstance()
	if (timezone != null) {
		calendar.timeZone = timezone
	}
	calendar.time = this
	calendar.set(Calendar.HOUR_OF_DAY, 0)
	calendar.set(Calendar.MINUTE, 0)
	calendar.set(Calendar.SECOND, 0)
	calendar.set(Calendar.MILLISECOND, 0)
	return calendar.time
}

fun Date.utcStartOfDay(): Date {
	return startOfDay(TimeZone.getTimeZone("UTC"))
}

fun Date.byAddingDays(numberOfDays: Int): Date {
	val calendar = Calendar.getInstance()
	calendar.time = this
	calendar.add(Calendar.DATE, numberOfDays)
	return calendar.time
}

fun Date.hoursDifference(date: Date?): Long {
	date ?: return Long.MAX_VALUE

	val hours = abs(this.time - date.time)
	return hours / 1000 / 60 / 60
}

fun Date.diffDayToDate(anotherDate: Date): Int {
	val minDate: Date
	val maxDate: Date
	if (this.before(anotherDate)) {
		minDate = this
		maxDate = anotherDate
	} else {
		minDate = anotherDate
		maxDate = this
	}
	val a = Calendar.getInstance().apply {
		time = minDate
	}
	val b = Calendar.getInstance().apply {
		time = maxDate
	}
	var diff = 0

	if (a.get(Calendar.YEAR) != b.get(Calendar.YEAR)) {
		diff += a.getActualMaximum(Calendar.DAY_OF_YEAR) - a.get(Calendar.DAY_OF_YEAR)
		a.add(Calendar.YEAR, 1)
		for (i in a.get(Calendar.YEAR) until b.get(Calendar.YEAR)) {
			diff += a.getActualMaximum(Calendar.DAY_OF_YEAR)
			a.add(Calendar.YEAR, 1)
		}
		diff += b.get(Calendar.DAY_OF_YEAR)
	} else {
		diff = b.get(Calendar.DAY_OF_YEAR) - a.get(Calendar.DAY_OF_YEAR)
	}
	return diff
}