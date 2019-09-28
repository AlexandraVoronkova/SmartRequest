package com.smartrequest.ui.widget

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity.CENTER_VERTICAL
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.smartrequest.R

class HorizontallyTitledValue : LinearLayout {

	var text: String
		get() = titleText.text.toString()
		set(value) {
			titleText.text = value
		}

	var value: String
		get() = valueText.text.toString()
		set(value) {
			valueText.text = value
		}

	private lateinit var titleText: TextView
	private lateinit var valueText: TextView

	//region ==================== Lifecycle =====================

	override fun onSaveInstanceState(): Parcelable? {
		val superState = super.onSaveInstanceState()
		val ss = CustomViewGroupSavedState(superState)
		ss.childrenStates = SparseArray()
		for (i in 0 until childCount) {
			getChildAt(i).saveHierarchyState(ss.childrenStates)
		}
		return ss
	}

	override fun onRestoreInstanceState(state: Parcelable) {
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

	//region ==================== Constructors =====================

	constructor(context: Context) : this(context, null)
	constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
	constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
		init(attrs, defStyle)
	}

	//endregion

	//region ==================== UI =====================

	private fun init(attrs: AttributeSet?, defStyle: Int) {
		orientation = HORIZONTAL
		gravity = CENTER_VERTICAL

		LayoutInflater.from(context).inflate(R.layout.view_horizontally_titled_value, this, true)

		titleText = findViewById(R.id.tv_title)
		valueText = findViewById(R.id.tv_value)

		val attributes = context.obtainStyledAttributes(
				attrs, R.styleable.HorizontallyTitledValue, defStyle, 0)

		val title = attributes.getString(R.styleable.HorizontallyTitledValue_htv_title)
		titleText.text = title

		val value = attributes.getString(R.styleable.HorizontallyTitledValue_htv_value)
		valueText.text = value

		attributes.recycle()
	}

	//endregion

}