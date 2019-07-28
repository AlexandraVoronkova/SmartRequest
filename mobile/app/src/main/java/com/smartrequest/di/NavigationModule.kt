package com.smartrequest.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class NavigationModule() {

	private val cicerone = Cicerone.create()
	
	@Provides
	@Singleton
	fun provideCicerone(): Cicerone<Router> {
		return cicerone
	}

	@Provides
	@Singleton
	fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
		return cicerone.navigatorHolder
	}

	@Provides
	@Singleton
	//@Named("AppRouter")
	fun provideRouter(cicerone: Cicerone<Router>): Router {
		return cicerone.router
	}
}