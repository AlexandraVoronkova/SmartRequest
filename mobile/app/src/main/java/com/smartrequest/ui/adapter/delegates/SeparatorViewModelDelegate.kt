package com.smartrequest.ui.adapter.delegates

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import com.smartrequest.R
import com.smartrequest.helpers.DimensHelper
import com.smartrequest.ui.adapter.ListViewModel

import javax.inject.Inject


class SeparatorViewModelDelegate @Inject constructor(private val context: Context) : AbsListItemAdapterDelegate<SeparatorViewModel, ListViewModel, SeparatorViewModelDelegate.ViewHolder>() {

	private val layoutInflater = LayoutInflater.from(context)

	//region ==================== Override ====================

	override fun isForViewType(item: ListViewModel, items: MutableList<ListViewModel>, position: Int): Boolean {
		return item is SeparatorViewModel
	}

	override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
		return ViewHolder(layoutInflater.inflate(R.layout.list_item_separator, parent, false))
	}

	override fun onBindViewHolder(item: SeparatorViewModel, viewHolder: ViewHolder, payloads: MutableList<Any>) {
		viewHolder.separator.setBackgroundResource(item.colorResId)
		viewHolder.itemView.layoutParams = viewHolder.itemView.layoutParams?.apply {
			height = DimensHelper.dpToPx(context, item.heightDp)
		}
	}

	//endregion

	//region ==================== ViewHolder ====================

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val separator: View = itemView.findViewById(R.id.separator)
	}

	//endregion


}