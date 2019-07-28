package com.smartrequest.ui.widget

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity.CENTER_VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import android.widget.TextView
import com.smartrequest.R

class FoldableTextView : LinearLayout {

	var title: String?
		set(title) = setViewTitle(title)
		get() = titleTextView.text.toString()

	var content: String?
		set(text) = setContentText(text)
		get() = foldableText.text.toString()

	var folded: Boolean
		set(isFolded) = setFoldableStatus(isFolded)
		get() = foldableText.visibility == View.GONE

	var titleClickListener: View.OnClickListener? = null

	private lateinit var titleTextView: TextView
	private lateinit var titleStatusSign: TextView
	private lateinit var llTitleBar: View

	private lateinit var foldableText: TextView

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

	//region ==================== Public =====================

	fun setViewModel(viewModel: FoldableTextViewModel) {
		viewModel.title?.let { title = it }
		viewModel.content?.let { content = it }
		folded = viewModel.folded
	}

	//endregion

	//region ==================== UI =====================

	private fun init(attrs: AttributeSet?, defStyle: Int) {
		orientation = VERTICAL
		gravity = CENTER_VERTICAL

		LayoutInflater.from(context).inflate(R.layout.view_foldable_text_view, this, true)

		titleTextView = findViewById(R.id.tv_title)
		titleStatusSign = findViewById(R.id.tv_title_sign)

		llTitleBar = findViewById(R.id.ll_title)
		val titleClickListener = titleClickListener
				?: OnClickListener { folded = !folded }
		llTitleBar.setOnClickListener(titleClickListener)

		foldableText = findViewById(R.id.tv_foldable_text)

		val attributes = context.obtainStyledAttributes(
				attrs, R.styleable.FoldableTextView, defStyle, 0)

		val title = attributes.getString(R.styleable.FoldableTextView_foldableTextTitle)
		this.title = title

		attributes.recycle()
	}

	private fun setContentText(text: String?) {
		foldableText.text = text
	}

	private fun setViewTitle(title: String?) {
		titleTextView.text = title
	}

	private fun setFoldableStatus(isFolded: Boolean) {
		val foldableTextVisibility = when (isFolded) {
			true -> GONE
			false -> VISIBLE
		}
		foldableText.visibility = foldableTextVisibility

		val openSign = when (isFolded) {
			true -> "+"
			false -> "-"
		}
		titleStatusSign.text = openSign
	}

	//endregion

	//region ==================== ViewModel =====================

	data class FoldableTextViewModel(var title: String? = null,
									 var content: String? = null,
									 var folded: Boolean = false)

	//endregion

}