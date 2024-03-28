package com.example.thecocktailapp.datasource

import com.example.thecocktailapp.model.CocktailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CocktailDataSource @Inject constructor(
    private val cocktailDataApi: CocktailDataApi
) {
    fun getCocktailData(s: String): Flow<Response<CocktailResponse>> = flow {
        val response = cocktailDataApi.getCocktailData(s)
        emit(response)

    }

    fun getCocktailDetailsById(i: String): Flow<Response<CocktailResponse>> = flow {
        val response = cocktailDataApi.getCocktailDetailsById(i)
        emit(response)
    }
}
