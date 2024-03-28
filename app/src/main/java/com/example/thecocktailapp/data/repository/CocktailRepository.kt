package com.example.thecocktailapp.repository

import android.app.Application
import com.example.thecocktailapp.datasource.CocktailDataSource
import com.example.thecocktailapp.model.CocktailResponse
import com.example.thecocktailapp.utils.FavoritePreferenceManager.cacheCocktailsPref
import com.example.thecocktailapp.utils.FavoritePreferenceManager.getFavoriteCocktailsPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val cocktailDataSource: CocktailDataSource,
    private val app: Application
) {

    suspend fun getCocktailDrinksBasedOnQueryStr(s: String): Flow<List<CocktailResponse.Drink>?> =

        cocktailDataSource.getCocktailData(s)
            .map { cocktails ->
                cocktails.body()?.drinks?.filter { it.strDrink?.contains(s, ignoreCase = true) ?: false }
            }
            .onEach {drinks ->
                cacheCocktailsPref(app, drinks)
            }
            .catch { exception -> emit(getFavoriteCocktailsPref(app)) }

    suspend fun getCocktailDetailsById(id: String): Flow<List<CocktailResponse.Drink>?> =
        cocktailDataSource.getCocktailDetailsById(id)
            .map { data ->
                data.body()?.drinks?.filter { it.idDrink == id }
            }.catch {it.stackTrace }
            .flowOn(Dispatchers.IO)
}

