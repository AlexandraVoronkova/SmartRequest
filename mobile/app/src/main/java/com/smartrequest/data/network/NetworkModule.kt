package com.smartrequest.data.network

import android.content.Context
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named


@Module
class NetworkModule {
	
	private companion object {
		
		val TIMEOUT = 30L
	}

	@Provides
	@Singleton
	@Named("nonAuth")
	fun provideAuthOkHttpClient(context: Context, @Named("time_zone_header_provider") timeZoneHeaderProvider: com.smartrequest.data.network.headers.HeaderProvider): OkHttpClient {
		val clientBuilder = OkHttpClient.Builder()

		if (com.smartrequest.data.Config.DEBUG) {
			val loggingInterceptor = HttpLoggingInterceptor()
			loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
			clientBuilder.addInterceptor(loggingInterceptor)

			clientBuilder.addInterceptor(ChuckInterceptor(context))
		}

		clientBuilder.addInterceptor { chain ->
			val requestBuilder = chain.request().newBuilder()

			requestBuilder.header("Content-Type", "application/json")
			requestBuilder.header("Accept", "application/json")

			val timeZoneHeader = timeZoneHeaderProvider.provideHeader()
			requestBuilder.header(timeZoneHeader.name, timeZoneHeader.value)

			val request = requestBuilder.build()
			return@addInterceptor chain.proceed(request)
		}

		clientBuilder.connectTimeout(com.smartrequest.data.network.NetworkModule.Companion.TIMEOUT, TimeUnit.SECONDS)
		clientBuilder.writeTimeout(com.smartrequest.data.network.NetworkModule.Companion.TIMEOUT, TimeUnit.SECONDS)
		clientBuilder.readTimeout(com.smartrequest.data.network.NetworkModule.Companion.TIMEOUT, TimeUnit.SECONDS)

		return clientBuilder.build()
	}
	
	@Provides
	@Singleton
	fun provideOkHttpClient(authInterceptor: com.smartrequest.data.network.AuthInterceptor,
                            @Named("nonAuth") okHttpClient: OkHttpClient): OkHttpClient {
		val clientBuilder = okHttpClient.newBuilder()

		clientBuilder.addInterceptor(authInterceptor)
		
		return clientBuilder.build()
	}
	
	@Provides
	@Singleton
	fun provideObjectMapper(): ObjectMapper {
		val objectMapper = ObjectMapper().apply {
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			setSerializationInclusion(JsonInclude.Include.ALWAYS)
			setVisibility(serializationConfig.defaultVisibilityChecker
					.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
					.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withSetterVisibility(JsonAutoDetect.Visibility.DEFAULT)
					.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withCreatorVisibility(JsonAutoDetect.Visibility.NONE))
			registerModule(KotlinModule())

			val module = SimpleModule()
			module.addDeserializer(Date::class.java, com.smartrequest.data.network.UnixTimestampDeserializer())
			module.addSerializer(Date::class.java, com.smartrequest.data.network.UnixTimestampSerializer())
			registerModule(module)
		}
		return objectMapper
	}
	
	@Provides
	@Singleton
	fun provideApiClient(okHttpClient: OkHttpClient, objectMapper: ObjectMapper): com.smartrequest.data.network.api.ApiClient {
		val apiClient = com.smartrequest.data.network.api.ApiClientImpl(okHttpClient, objectMapper, com.smartrequest.data.Config.SERVER_BASE_URL)
		return apiClient
	}

	@Provides
	@Singleton
	@Named("nonAuth")
	fun provideNonAuthApiClient(@Named("nonAuth") okHttpClient: OkHttpClient, objectMapper: ObjectMapper): com.smartrequest.data.network.api.ApiClient {
		val apiClient = com.smartrequest.data.network.api.ApiClientImpl(okHttpClient, objectMapper, com.smartrequest.data.Config.SERVER_BASE_URL)
		return apiClient
	}

	@Provides
	@Singleton
	@Named("time_zone_header_provider")
	fun provideTimeZoneHeaderProvider(): com.smartrequest.data.network.headers.HeaderProvider {
		return com.smartrequest.data.network.headers.TimeZoneHeaderProvider()
	}
	
}