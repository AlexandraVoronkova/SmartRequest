package com.smartrequest.ui.adapter.delegates

import android.support.annotation.ColorRes
import com.smartrequest.ui.adapter.ListViewModel


data class VerticalSeparatorViewModel(@ColorRes val colorResId: Int,
                                      val widthDp: Int = 1) : ListViewModel {
}