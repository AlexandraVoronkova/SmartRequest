package {{.answers.appPackageName}}.di

import android.content.Context
import dagger.Module
import dagger.Provides
import {{.answers.appPackageName}}.components.AppContext
import javax.inject.Singleton

@Module
class AppModule(private val application: AppContext) {
	
	@Provides
	@Singleton
	fun provideContext(): Context {
		return application
	}
}