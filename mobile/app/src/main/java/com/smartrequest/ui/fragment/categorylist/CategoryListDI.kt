package com.smartrequest.ui.fragment.categorylist

import android.app.Activity
import android.content.Context
import com.smartrequest.di.NamedDependencies
import com.smartrequest.ui.adapter.listener.ListItemClickListener
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Subcomponent(modules = [CategoryListModule::class])
interface CategoryListComponent {

	fun inject(fragment: CategoryListFragment)

}

@Module
class CategoryListModule(private val activity: Activity,
                         private val listItemClickListener: ListItemClickListener) {


    @Provides
    @Named(NamedDependencies.ACTIVITY_CONTEXT)
    fun activityContext(): Context {
        return activity
    }

	@Provides
	fun presenter(categoryListPresenter: CategoryListPresenter): CategoryListContract.Presenter {
		return categoryListPresenter
	}

    @Provides
    fun provideListItemClickListener(): ListItemClickListener {
        return listItemClickListener
    }

}