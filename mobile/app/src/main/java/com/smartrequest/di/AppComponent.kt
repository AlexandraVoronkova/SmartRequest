package com.smartrequest.di

import com.smartrequest.ui.activity.auth.AuthScreenComponent
import com.smartrequest.ui.activity.auth.AuthScreenModule
import com.smartrequest.ui.activity.authoptions.AuthOptionsScreenComponent
import com.smartrequest.ui.activity.authoptions.AuthOptionsScreenModule
import com.smartrequest.ui.activity.base.BaseScreenComponent
import com.smartrequest.ui.activity.base.BaseScreenModule
import com.smartrequest.ui.activity.container.ContainerScreenComponent
import com.smartrequest.ui.activity.container.ContainerScreenModule
import com.smartrequest.ui.activity.main.MainScreenComponent
import com.smartrequest.ui.activity.main.MainScreenModule
import com.smartrequest.ui.activity.splash.SplashScreenComponent
import com.smartrequest.ui.activity.splash.SplashScreenModule
import com.smartrequest.ui.fragment.addresslist.AddressListComponent
import com.smartrequest.ui.fragment.addresslist.AddressListModule
import com.smartrequest.ui.fragment.authoptions.AuthOptionsComponent
import com.smartrequest.ui.fragment.authoptions.AuthOptionsModule
import com.smartrequest.ui.fragment.call.CallComponent
import com.smartrequest.ui.fragment.call.CallModule
import com.smartrequest.ui.fragment.categorylist.CategoryListComponent
import com.smartrequest.ui.fragment.categorylist.CategoryListModule
import com.smartrequest.ui.fragment.inputtext.InputTextComponent
import com.smartrequest.ui.fragment.inputtext.InputTextModule
import com.smartrequest.ui.fragment.login.LoginComponent
import com.smartrequest.ui.fragment.login.LoginModule
import com.smartrequest.ui.fragment.preloader.PreloaderComponent
import com.smartrequest.ui.fragment.preloader.PreloaderModule
import com.smartrequest.ui.fragment.registration.RegistrationComponent
import com.smartrequest.ui.fragment.registration.RegistrationModule
import com.smartrequest.ui.fragment.requestlist.RequestListComponent
import com.smartrequest.ui.fragment.requestlist.RequestListModule
import com.smartrequest.ui.fragment.tabcontainer.TabContainerFragment
import com.smartrequest.ui.fragment.userdata.UserDataComponent
import com.smartrequest.ui.fragment.userdata.UserDataModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [com.smartrequest.di.AppModule::class, com.smartrequest.di.NavigationModule::class, com.smartrequest.di.LocalNavigationModule::class,
	com.smartrequest.data.network.NetworkModule::class, com.smartrequest.data.AppDataModule::class])
interface AppComponent {


	fun inject(appContext: com.smartrequest.components.AppContext)

	//region ==================== Splash ====================

	fun plus(module: SplashScreenModule): SplashScreenComponent

	//endregion


	//region ==================== Auth ====================

	fun plus(module: AuthOptionsScreenModule): AuthOptionsScreenComponent

	fun plus(module: AuthOptionsModule): AuthOptionsComponent

	fun plus(module: AuthScreenModule): AuthScreenComponent

	fun plus(module: LoginModule): LoginComponent

	fun plus(module: RegistrationModule): RegistrationComponent

	fun plus(module: CategoryListModule): CategoryListComponent

	//endregion

	//region ===================== Shared ======================

	fun plus(module: BaseScreenModule): BaseScreenComponent

	fun plus(module: ContainerScreenModule): ContainerScreenComponent

	fun plus(module: PreloaderModule): PreloaderComponent

	//endregion

	//region ==================== Main ====================

	fun plus(module: MainScreenModule): MainScreenComponent

	fun inject(tabContainerFragment: TabContainerFragment)

	fun plus(module: UserDataModule): UserDataComponent

	fun plus(module: CallModule): CallComponent

	fun plus(module: InputTextModule): InputTextComponent

	fun plus(module: AddressListModule): AddressListComponent

	fun plus(module: RequestListModule): RequestListComponent

	//endregion


}