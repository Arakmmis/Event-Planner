package task.swenson.eventplanner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import task.swenson.eventplanner.data.remote.Api
import task.swenson.eventplanner.data.remote.RemoteDataSource
import task.swenson.eventplanner.data.remote.Url
import task.swenson.eventplanner.domain.data_source.IRemoteDataSource
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    private const val CONNECTION_TIMEOUT: Int = 10000

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: Api): IRemoteDataSource =
        RemoteDataSource(api)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

    @Provides
    @Singleton
    fun provideHttpClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(logger)
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Url.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)
}