package com.smartrequest.ui.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.smartrequest.R
import android.os.Parcelable
import android.util.SparseArray


class LynchTitledInput : LinearLayout {

	lateinit var titleText: TextView
		private set

	lateinit var editText: EditText
		private set

	var text: String
		get() = editText.text.toString()
		set(value) = editText.setText(value)

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

	override fun setEnabled(enabled: Boolean) {
		super.setEnabled(enabled)
		editText.isEnabled = enabled

		if (enabled) {
			editText.setTextColor(ContextCompat.getColor(context, R.color.charcoal))
		} else {
			editText.setTextColor(ContextCompat.getColor(context, R.color.lynch))
		}
	}

	//endregion

	//region ==================== UI ====================

	private fun init(attrs: AttributeSet?, defStyle: Int) {
		orientation = VERTICAL

		LayoutInflater.from(context).inflate(R.layout.lynch_titled_input, this, true)

		titleText = findViewById(R.id.tv_title)
		editText = findViewById(R.id.et_text)

		val attributes = context.obtainStyledAttributes(
				attrs, R.styleable.TitledInput, defStyle, 0)

		val title = attributes.getString(R.styleable.TitledInput_title)
		titleText.text = title

		val hint = attributes.getString(R.styleable.TitledInput_hint)
		editText.hint = hint

		if (attributes.hasValue(R.styleable.TitledInput_android_inputType)) {
			val inputType = attributes.getInt(R.styleable.TitledInput_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
			editText.inputType = inputType
		}

		if (attributes.hasValue(R.styleable.TitledInput_android_imeOptions)) {
			val imeOptions =
					attributes.getInt(R.styleable.TitledInput_android_imeOptions, 0)
			editText.imeOptions = imeOptions
		}

		//setup this parameters in code, because they are broken if input type is textPassword. See https://stackoverflow.com/a/3444882/1417307
		editText.typeface = ResourcesCompat.getFont(context, R.font.pfdin_display_pro_light)
		editText.setHintTextColor(ContextCompat.getColor(context, R.color.charcoal_50_percent_transparent))

	}

	//endregion

}