package com.example.thecocktailapp.data.datasource

import android.app.Application
import com.example.thecocktailapp.data.model.CocktailResponse
import com.example.thecocktailapp.utils.PreferenceManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalCocktailDataSource @Inject constructor(
    private val preference: PreferenceManager,
    private val app: Application
) {

    fun getCachedCocktailData(): Flow<List<CocktailResponse.Drink>?> = flow {
       val response = preference.getFavoriteCocktailsPref(app)
        emit(response)
    }

    fun cacheCocktailsPref(drinks: List<CocktailResponse.Drink>?) = flow {
        val data = preference.cacheCocktailsPref(app, drinks)
        emit(data)
    }
}
