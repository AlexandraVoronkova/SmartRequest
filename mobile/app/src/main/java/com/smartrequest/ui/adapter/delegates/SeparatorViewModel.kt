package com.smartrequest.ui.adapter.delegates

import android.support.annotation.ColorRes
import com.smartrequest.ui.adapter.ListViewModel


data class SeparatorViewModel(@ColorRes val colorResId: Int,
                              val heightDp: Int = 1) : ListViewModel {
}