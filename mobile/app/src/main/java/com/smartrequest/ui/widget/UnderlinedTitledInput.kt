package com.smartrequest.ui.widget

import android.content.Context
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.smartrequest.R
import com.smartrequest.extensions.appspecific.configurePhoneNumberFormatter
import com.smartrequest.helpers.DimensHelper

class UnderlinedTitledInput : LinearLayout {

	lateinit var titleText: TextView
		private set

	lateinit var editText: EditText
		private set

	var text: String
		get() = editText.text.toString()
		set(value) = editText.setText(value)

	private var mListeners: MutableList<TextWatcher> = mutableListOf()

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

	//region ==================== Public ====================

	override fun setOnClickListener(onClickListener: OnClickListener?) {
		super.setOnClickListener(onClickListener)
		editText.inputType = InputType.TYPE_NULL
		editText.isFocusable = false
		editText.isFocusableInTouchMode = false
		editText.setOnClickListener(onClickListener)
		editText.setHorizontallyScrolling(false)
		editText.maxLines = 20
		editText.setPadding(editText.paddingLeft, editText.paddingTop, DimensHelper.dpToPx(context, 20), editText.paddingBottom)
	}

	fun setTitle(title: String?) {
		titleText.text = title
	}

	fun setHint(hint: String?) {
		editText.hint = hint
	}

	fun addTextChangedListener(watcher: TextWatcher) {
		mListeners.add(watcher)
		editText.addTextChangedListener(watcher)
	}

	fun clearTextChangedListeners() {
		for (watcher in mListeners) {
			editText.removeTextChangedListener(watcher)
		}
		mListeners.clear()
	}

	fun configurePhoneNumberFormatter(needFocusChangeListener: Boolean = true) {
		mListeners.add(editText.configurePhoneNumberFormatter(needFocusChangeListener))
	}


	//endregion

	//region ==================== UI ====================

	private fun init(attrs: AttributeSet?, defStyle: Int) {
		orientation = VERTICAL

		LayoutInflater.from(context).inflate(R.layout.underlined_titled_input, this, true)

		titleText = findViewById(R.id.tv_title)
		editText = findViewById(R.id.et_text)

		val attributes = context.obtainStyledAttributes(
				attrs, R.styleable.UnderlinedTitledInput, defStyle, 0)

		val title = attributes.getString(R.styleable.UnderlinedTitledInput_uti_title)
		titleText.text = title

		val hint = attributes.getString(R.styleable.UnderlinedTitledInput_uti_hint)
		editText.hint = hint

		if (attributes.hasValue(R.styleable.UnderlinedTitledInput_android_inputType)) {
			val inputType = attributes.getInt(R.styleable.UnderlinedTitledInput_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
			editText.inputType = inputType
		}

		if (attributes.hasValue(R.styleable.UnderlinedTitledInput_android_imeOptions)) {
			val inputType =
					attributes.getInt(R.styleable.UnderlinedTitledInput_android_imeOptions, 0);
			editText.imeOptions = inputType
		}

		//setup this parameters in code, because they are broken if input type is textPassword. See https://stackoverflow.com/a/3444882/1417307
		editText.typeface = ResourcesCompat.getFont(context, R.font.pfdin_display_pro_light)
		editText.setHintTextColor(ContextCompat.getColor(context, R.color.charcoal_50_percent_transparent))

	}

	//endregion

}