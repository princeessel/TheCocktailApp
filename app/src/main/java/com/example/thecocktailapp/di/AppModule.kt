package com.example.thecocktailapp.di

import android.app.Application
import com.example.thecocktailapp.data.datasource.CocktailDataApi
import com.example.thecocktailapp.data.datasource.CocktailDataSource
import com.example.thecocktailapp.data.datasource.LocalCocktailDataSource
import com.example.thecocktailapp.data.repository.CocktailRepository
import com.example.thecocktailapp.utils.Constant.BASE_URL
import com.example.thecocktailapp.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesCocktailRepository(
        cocktailDataSource: CocktailDataSource,
        localDataSource: LocalCocktailDataSource
    ): CocktailRepository =
        CocktailRepository(cocktailDataSource, localDataSource)

    @Singleton
    @Provides
    fun providesCocktailDataSource(
        cocktailDataApi: CocktailDataApi
    ): CocktailDataSource = CocktailDataSource(cocktailDataApi)

    @Singleton
    @Provides
    fun providesLocalDataSource(
        manager: PreferenceManager,
        application: Application
    ): LocalCocktailDataSource = LocalCocktailDataSource(manager, application)

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(): CocktailDataApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailDataApi::class.java)
    }

    @Singleton
    @Provides
    fun providesPrefManager(): PreferenceManager = PreferenceManager
}
