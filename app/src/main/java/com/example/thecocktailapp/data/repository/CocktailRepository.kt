package com.example.thecocktailapp.data.repository

import com.example.thecocktailapp.data.datasource.CocktailDataSource
import com.example.thecocktailapp.data.datasource.LocalCocktailDataSource
import com.example.thecocktailapp.data.model.CocktailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val cocktailDataSource: CocktailDataSource,
    private val localDataSource: LocalCocktailDataSource
) {

    fun getCocktailDrinksBasedOnQueryStr(s: String): Flow<List<CocktailResponse.Drink>?> =
        cocktailDataSource.getCocktailData(s)
            .map { cocktails ->
                cocktails.body()?.drinks
            }
            .onEach { drinks -> localDataSource.cacheCocktailsPref(drinks) }
            .catch { exception -> exception.message }
            .flowOn(Dispatchers.IO)

    fun getCachedCocktailData(s: String): Flow<List<CocktailResponse.Drink>?> =
        localDataSource.getCachedCocktailData()
            .map { drinks ->
                drinks?.filter {
                    it.strDrink?.contains(s, ignoreCase = true) ?: false
                }
            }

    fun getCocktailDetailsById(id: String): Flow<List<CocktailResponse.Drink>?> =
        cocktailDataSource.getCocktailDetailsById(id)
            .map { data ->
                data.body()?.drinks?.filter { it.idDrink == id }
            }
            .catch { it.localizedMessage }
            .flowOn(Dispatchers.IO)
}
