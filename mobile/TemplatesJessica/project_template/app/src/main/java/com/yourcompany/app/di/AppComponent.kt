package {{.answers.appPackageName}}.di

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, NavigationModule::class,
		LocalNavigationModule::class))
interface AppComponent {
	
}