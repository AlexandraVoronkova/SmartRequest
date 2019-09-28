package com.smartrequest.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager

open class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	
	protected var delegatesManager: AdapterDelegatesManager<List<ListViewModel>> = AdapterDelegatesManager()
	protected var items: MutableList<ListViewModel> = ArrayList()
		private set
	
	override fun getItemViewType(position: Int): Int {
		return delegatesManager.getItemViewType(items, position)
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return delegatesManager.onCreateViewHolder(parent, viewType)
	}
	
	
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		delegatesManager.onBindViewHolder(items, position, holder)
	}
	
	override fun getItemCount(): Int {
		return items.size
	}
	
	open fun swapItems(items: List<ListViewModel>) {
		this.items.clear()
		this.items.addAll(items)
		notifyDataSetChanged()
	}
}
