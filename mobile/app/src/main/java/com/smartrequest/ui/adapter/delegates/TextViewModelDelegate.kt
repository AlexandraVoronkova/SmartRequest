package com.smartrequest.ui.adapter.delegates

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import com.smartrequest.R
import com.smartrequest.ui.adapter.ListViewModel

import javax.inject.Inject
import javax.inject.Named


class TextViewModelDelegate @Inject constructor(@Named(com.smartrequest.di.NamedDependencies.ACTIVITY_CONTEXT) private val context: Context,
                                                private val itemClickListener: com.smartrequest.ui.adapter.listener.ListItemClickListener? = null) : AbsListItemAdapterDelegate<TextViewModel, ListViewModel, TextViewModelDelegate.ViewHolder>() {

	private val layoutInflater = LayoutInflater.from(context)

	//region ==================== Override ====================

	override fun isForViewType(item: ListViewModel, items: MutableList<ListViewModel>, position: Int): Boolean {
		return item is TextViewModel
	}

	override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
		return ViewHolder(layoutInflater.inflate(R.layout.list_item_text, parent, false))
	}

	override fun onBindViewHolder(item: TextViewModel, viewHolder: ViewHolder, payloads: MutableList<Any>) {
		if (itemClickListener != null) {
			viewHolder.itemView.setOnClickListener { view ->
				itemClickListener.onListItemClicked(item)
			}
		} else {
			viewHolder.itemView.setOnClickListener(null)
		}

		viewHolder.tvText.text = item.text
		if (item.textSizeDp != null) {
			viewHolder.tvText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, item.textSizeDp.toFloat())
		}
		if (item.textColorResId != null) {
			viewHolder.tvText.setTextColor(ContextCompat.getColor(context, item.textColorResId))
		}
		if (item.fontResId != null) {
			viewHolder.tvText.typeface = ResourcesCompat.getFont(context, item.fontResId)
		}
	}

	//endregion

	//region ==================== ViewHolder ====================

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val tvText: TextView = itemView.findViewById(R.id.tv_text)

	}

	//endregion


}