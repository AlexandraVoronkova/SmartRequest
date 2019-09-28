package com.smartrequest.components

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import com.smartrequest.di.AppModule
import com.crashlytics.android.Crashlytics
import com.smartrequest.BuildConfig
import com.smartrequest.R
import com.smartrequest.di.AppComponent
import com.smartrequest.di.DaggerAppComponent
import com.smartrequest.ui.other.UpScreenMessageTypes
import com.smartrequest.utils.FileHelper
import io.fabric.sdk.android.Fabric
import io.mobilife.upscreenmessage.UpScreenMessage
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject


class AppContext : MultiDexApplication(), LifecycleObserver {

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
		appComponent.inject(this)

		FileHelper.initialize(this, this.packageName + ".fileprovider")

		ProcessLifecycleOwner.get().lifecycle.addObserver(this)

		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

		UpScreenMessage.initialize(R.id.up_screen_message_id)
		UpScreenMessage.registerMessageType(UpScreenMessageTypes.TYPE_ERROR,
				UpScreenMessage.MessageParams(R.layout.up_screen_message, R.id.tv_message,
						R.color.outrageous_orange, R.color.white))
		UpScreenMessage.registerMessageType(UpScreenMessageTypes.TYPE_WARNING,
				UpScreenMessage.MessageParams(R.layout.up_screen_message, R.id.tv_message,
						R.color.buttercup, R.color.white))
		UpScreenMessage.registerMessageType(UpScreenMessageTypes.TYPE_SUCCESS,
				UpScreenMessage.MessageParams(R.layout.up_screen_message, R.id.tv_message,
						R.color.lime_green, R.color.white))

		RxJavaPlugins.setErrorHandler { e ->
			Timber.w("Global Rx Error: ")
			e.printStackTrace()
			if (e is UndeliverableException) {
				Timber.w("Undeliverable exception received, not sure what to do", e)
			}
		}

	}

	private fun initDaggerComponent(): com.smartrequest.di.AppComponent {
		return DaggerAppComponent.builder().appModule(AppModule(this)).build()
	}

	//region ===================== LifecycleObserver ======================

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	fun startSomething() {
		Timber.v("APP IS ON FOREGROUND")
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
	fun stopSomething() {
		Timber.v("APP IS IN BACKGROUND")
	}

	//endregion


}