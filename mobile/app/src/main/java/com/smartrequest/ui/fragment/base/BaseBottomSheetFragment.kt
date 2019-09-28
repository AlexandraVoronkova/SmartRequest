package com.smartrequest.ui.fragment.base

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog

open class BaseBottomSheetFragment(): BaseDialogFragment() {

	//region ==================== Lifecycle callbacks ====================

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		return BottomSheetDialog(context!!, theme)
	}

	//endregion

}