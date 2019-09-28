package com.smartrequest.ui.other.resources

import android.support.annotation.StringRes

interface ResourceProvider {

	fun getString(@StringRes stringResId: Int): String

	fun getString(@StringRes stringResId: Int, vararg formatArgs: Any): String

}