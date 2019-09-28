package com.smartrequest.helpers

import android.content.Context
import android.util.Patterns
import com.smartrequest.R
import com.smartrequest.extensions.shared.byAddingDays
import com.smartrequest.extensions.shared.startOfDay
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


object FormatHelper {

	const val RUSSIAN_PHONE_NUMBER_FORMAT = "+[0] ([000]) [009] [99] [99…]"

	const val CARD_NUMBER_FORMAT = "[0000] [0000] [0000] [0000…]"

	const val CARD_EXPIRED_DATE_FORMAT = "[00]/[00]"

	private const val SHORT_DATE_FORMAT = "dd MMMM"
	private const val FULL_DATE_FORMAT = "dd MMMM yyyy"

	//region ==================== E-mail =====================

	fun isValidEmail(text: String): Boolean {
		return Patterns.EMAIL_ADDRESS.matcher(text).matches()
	}

	//endregion

	//region ==================== Phone number =====================

	fun isValidPhoneNumber(text: String): Boolean {
		return getExtractedPhoneNumber(text).length >= 6
	}

	fun getExtractedPhoneNumber(text: String): String {
		return extractNumbers(text)
	}

	fun getFormattedPhoneNumber(phoneNumber: String): String {
		val mask = Mask(RUSSIAN_PHONE_NUMBER_FORMAT)
		val result = mask.apply(
				CaretString(
						phoneNumber,
						phoneNumber.length
				),
				true // you may consider disabling autocompletion for your case
		)
		return result.formattedText.string
	}

	//endregion

	//region ===================== Date ======================

	fun getFormattedDate(date: Date): String {
		return getFormattedDate(date, "dd MMMM yyyy")
	}

	fun getFormattedTime(date: Date): String {
		return getFormattedDate(date, "HH:mm")
	}

	fun getFormattedTime(date: Date, timeZone: TimeZone? = null): String {
		return getFormattedDate(date, "HH:mm", timeZone)
	}

	fun getFormattedDate(date: Date, format: String, timeZone: TimeZone? = null): String {
		val simpleDateFormat = SimpleDateFormat(format)
		if (timeZone != null) {
			simpleDateFormat.timeZone = timeZone
		}
		return simpleDateFormat.format(date)
	}

	fun getRelativeDateString(context: Context, date: Date): String {
		val calendar = Calendar.getInstance().apply {
			time = date.startOfDay()
		}
		val currentCalendar = Calendar.getInstance().apply {
			time = Date().startOfDay()
		}

		return when {
			(calendar.time == currentCalendar.time) -> {
				context.resources.getString(R.string.relative_date_string_today)
			}
			(calendar.time == currentCalendar.time.byAddingDays(-1)) -> {
				context.resources.getString(R.string.relative_date_string_yesterday)
			}
			(calendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)) -> {
				getFormattedDate(date, SHORT_DATE_FORMAT)
			}
			else -> {
				getFormattedDate(date, FULL_DATE_FORMAT)
			}
		}
	}

	//endregion

	//region ==================== Price =====================

	fun getThousandSeparatedNumber(price: Long): String {
		val formatter = DecimalFormat("##,###")
		val symbols = formatter.decimalFormatSymbols

		symbols.groupingSeparator = ' '
		formatter.decimalFormatSymbols = symbols
		return formatter.format(price)
	}

	//endregion

	//region ==================== Credit card =====================

	fun isValidCardNumber(cardNumber: String): Boolean {
		return getExtractedCardNumber(cardNumber).length >= 16
	}

	fun getExtractedCardNumber(cardNumber: String): String {
		return extractNumbers(cardNumber)
	}

	fun getFormattedCardNumber(cardNumber: String): String {
		val mask = Mask(CARD_NUMBER_FORMAT)
		val result = mask.apply(
				CaretString(
						cardNumber,
						cardNumber.length
				),
				true // you may consider disabling autocompletion for your case
		)
		return result.formattedText.string
	}

	fun isValidExpiredDate(date: String): Boolean {
		return getExtractedExpiredDate(date).length == 4
	}

	fun getExtractedExpiredDate(date: String): String {
		return extractNumbers(date)
	}

	fun getFormattedExpiredDate(date: String): String {
		val mask = Mask(CARD_EXPIRED_DATE_FORMAT)
		val result = mask.apply(
				CaretString(
						date,
						date.length
				), true)

		return result.formattedText.string
	}

	//endregion

	//region ===================== Timezone ======================

	fun formatTimeZoneOffset(offset: String?): String? {
		if (offset == null || offset.length != 5) {
			return offset
		}
		return "GMT ${offset.substring(0, 3)}:${offset.substring(3, 5)}"
	}

	//endregion

	//region ==================== Internal =====================

	private fun extractNumbers(s: String): String {
		return s.replace("[^0-9]".toRegex(), "")
	}

	//endregion

}