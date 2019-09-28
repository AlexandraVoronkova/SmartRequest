package {{.answers.appPackageName}}.di

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import {{.answers.appPackageName}}.ui.navigation.LocalCiceroneHolder

@Module
class LocalNavigationModule {

	@Provides
	@Singleton
	internal fun provideLocalNavigationHolder(): LocalCiceroneHolder {
		return LocalCiceroneHolder()
	}
}
