package {{.answers.appPackageName}}.ui.adapter

import android.support.v7.util.DiffUtil


open class DiffAdapter : BaseAdapter() {

	override fun swapItems(items: List<ListViewModel>) {
		val diffResult = DiffUtil.calculateDiff(ListViewModelDiffCallback(this.items, items))

		this.items.clear()
		this.items.addAll(items)

		diffResult.dispatchUpdatesTo(this)
	}
}
