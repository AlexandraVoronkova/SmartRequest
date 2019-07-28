package com.smartrequest.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.smartrequest.R
import android.os.Parcelable
import android.util.SparseArray


class VerticallyTitledValue: LinearLayout {

	lateinit var titleText: TextView
		private set

	lateinit var valueText: TextView
		private set

	var text: String
		get() = valueText.text.toString()
		set(value) = valueText.setText(value)

	//region ===================== Constructors ======================

	constructor(context: Context) : super(context) {
		init(null, 0)
	}

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
		init(attrs, 0)
	}

	constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
		init(attrs, defStyle)
	}

	//endregion

	//region ==================== Lifecycle ====================

	public override fun onSaveInstanceState(): Parcelable? {
		val superState = super.onSaveInstanceState()
		val ss = CustomViewGroupSavedState(superState)
		ss.childrenStates = SparseArray()
		for (i in 0 until childCount) {
			getChildAt(i).saveHierarchyState(ss.childrenStates)
		}
		return ss
	}

	public override fun onRestoreInstanceState(state: Parcelable) {
		val ss = state as CustomViewGroupSavedState
		super.onRestoreInstanceState(ss.superState)
		for (i in 0 until childCount) {
			getChildAt(i).restoreHierarchyState(ss.childrenStates)
		}
	}

	override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
		dispatchFreezeSelfOnly(container)
	}

	override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
		dispatchThawSelfOnly(container)
	}

	//endregion

	//region ==================== UI ====================

	private fun init(attrs: AttributeSet?, defStyle: Int) {
		orientation = VERTICAL

		LayoutInflater.from(context).inflate(R.layout.vertically_titled_value, this, true)

		titleText = findViewById(R.id.tv_title)
		valueText = findViewById(R.id.tv_value)

		val attributes = context.obtainStyledAttributes(
				attrs, R.styleable.VerticallyTitledValue, defStyle, 0)

		val title = attributes.getString(R.styleable.VerticallyTitledValue_vtv_title)
		titleText.text = title

		val value = attributes.getString(R.styleable.VerticallyTitledValue_vtv_value)
		valueText.text = value
	}

	//endregion

}