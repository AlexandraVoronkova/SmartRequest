package com.smartrequest.ui.other.resources

import android.content.Context
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(private val context: Context) : ResourceProvider {

	override fun getString(stringResId: Int): String {
		return context.getString(stringResId)
	}

	override fun getString(stringResId: Int, vararg formatArgs: Any): String {
		return context.getString(stringResId, *formatArgs)
	}

}