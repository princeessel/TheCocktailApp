package com.example.thecocktailapp.di

import android.app.Application
import com.example.thecocktailapp.datasource.CocktailDataApi
import com.example.thecocktailapp.datasource.CocktailDataSource
import com.example.thecocktailapp.repository.CocktailRepository
import com.example.thecocktailapp.utils.Constant.BASE_URL
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
    fun providesCocktailRepository(cocktailDataSource: CocktailDataSource, app: Application): CocktailRepository =
        CocktailRepository(cocktailDataSource, app)

    @Singleton
    @Provides
    fun providesCocktailDataSource(
        cocktailDataApi: CocktailDataApi
    ): CocktailDataSource = CocktailDataSource(cocktailDataApi)

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
    }
