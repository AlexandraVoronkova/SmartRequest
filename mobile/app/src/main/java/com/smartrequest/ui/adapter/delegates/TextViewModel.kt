package com.smartrequest.ui.adapter.delegates

import android.support.annotation.ColorRes
import android.support.annotation.FontRes
import com.smartrequest.ui.adapter.ListViewModel


data class TextViewModel(override var listItemId: String?,
                         val text: String?,
                         val textSizeDp: Int? = null,
                         @ColorRes val textColorResId: Int? = null,
                         @FontRes val fontResId: Int? = null) : ListViewModel {
}