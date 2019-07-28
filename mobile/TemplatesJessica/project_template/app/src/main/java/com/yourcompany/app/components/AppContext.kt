package {{.answers.appPackageName}}.components

import android.app.Application
import com.crashlytics.android.Crashlytics
import {{.answers.appPackageName}}.BuildConfig
import io.fabric.sdk.android.Fabric
import {{.answers.appPackageName}}.di.AppComponent
import {{.answers.appPackageName}}.di.AppModule
import {{.answers.appPackageName}}.di.DaggerAppComponent
import timber.log.Timber


class AppContext : Application() {

	lateinit var appComponent: AppComponent
		private set
	
	companion object {
		lateinit var instance: AppContext
			private set
	}
	
	override fun onCreate() {
		super.onCreate()
		instance = this
		
		Fabric.with(this, Crashlytics())
		
		// configure Timber
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}

		this.appComponent = initDaggerComponent()
		
	}

	private fun initDaggerComponent(): AppComponent {
		return DaggerAppComponent.builder().appModule(AppModule(this)).build()
	}
	
	
}