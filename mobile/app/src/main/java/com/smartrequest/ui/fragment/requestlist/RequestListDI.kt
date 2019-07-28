package com.smartrequest.ui.fragment.requestlist

import android.app.Activity
import android.content.Context
import com.smartrequest.di.NamedDependencies
import com.smartrequest.ui.adapter.listener.ListItemClickListener
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Subcomponent(modules = [RequestListModule::class])
interface RequestListComponent {

	fun inject(fragment: RequestListFragment)

}

@Module
class RequestListModule(private val activity: Activity,
                         private val listItemClickListener: ListItemClickListener) {


    @Provides
    @Named(NamedDependencies.ACTIVITY_CONTEXT)
    fun activityContext(): Context {
        return activity
    }

	@Provides
	fun presenter(addressListPresenter: RequestListPresenter): RequestListContract.Presenter {
		return addressListPresenter
	}

    @Provides
    fun provideListItemClickListener(): ListItemClickListener {
        return listItemClickListener
    }

}