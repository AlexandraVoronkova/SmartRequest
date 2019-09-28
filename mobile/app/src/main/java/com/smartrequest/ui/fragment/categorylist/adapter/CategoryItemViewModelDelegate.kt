package com.smartrequest.ui.fragment.categorylist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import com.smartrequest.R
import com.smartrequest.di.NamedDependencies
import com.smartrequest.helpers.DimensHelper
import com.smartrequest.ui.adapter.ListViewModel
import com.smartrequest.ui.adapter.listener.ListItemClickListener
import javax.inject.Inject
import javax.inject.Named

class CategoryItemViewModelDelegate @Inject constructor(@Named(NamedDependencies.ACTIVITY_CONTEXT) private val context: Context,
														private val itemClickListener: ListItemClickListener? = null) :
		AbsListItemAdapterDelegate<CategoryItemViewModel, ListViewModel, CategoryItemViewModelDelegate.ViewHolder>() {

	private val layoutInflater = LayoutInflater.from(context)

	//region ==================== Override =====================

	override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
		return ViewHolder(layoutInflater.inflate(R.layout.list_item_category, parent, false))
	}

	override fun isForViewType(item: ListViewModel, items: MutableList<ListViewModel>, position: Int): Boolean {
		return item is CategoryItemViewModel
	}

	override fun onBindViewHolder(item: CategoryItemViewModel, viewHolder: ViewHolder, payloads: MutableList<Any>) {

		if (itemClickListener != null) {
			viewHolder.itemView.setOnClickListener {
				itemClickListener.onListItemClicked(item)
			}
		} else {
			viewHolder.itemView.setOnClickListener(null)
		}

		viewHolder.title.text = item.name

		if (item.isParent) {
			viewHolder.indicator.layoutParams = (viewHolder.indicator.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
				leftMargin = DimensHelper.dpToPx(context, 0)
			}
		} else {
			viewHolder.indicator.layoutParams = (viewHolder.indicator.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
				leftMargin = DimensHelper.dpToPx(context, 16)
			}
		}
	}
	//endregion

	//region ==================== Viewholder =====================

	class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
		val title: TextView = view.findViewById(R.id.tv_title)
		val indicator: View = view.findViewById(R.id.indicator)
	}

	//endregion

}