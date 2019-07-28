package {{.answers.appPackageName}}.ui.adapter.listener


import {{.answers.appPackageName}}.ui.adapter.ListViewModel

interface ListItemClickListener {
	fun onListItemClicked(delegateViewModel: ListViewModel)
}
