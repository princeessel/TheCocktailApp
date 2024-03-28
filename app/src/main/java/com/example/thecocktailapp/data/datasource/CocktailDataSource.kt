package com.example.thecocktailapp.data.datasource

import com.example.thecocktailapp.data.model.CocktailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CocktailDataSource @Inject constructor(
    private val cocktailDataApi: CocktailDataApi
) {
    fun getCocktailData(s: String): Flow<Response<CocktailResponse>> = flow {
        val response = cocktailDataApi.getCocktailData(s)
        if (response.isSuccessful) {
            emit(response)
        }
    }

    fun getCocktailDetailsById(i: String): Flow<Response<CocktailResponse>> = flow {
        val response = cocktailDataApi.getCocktailDetailsById(i)
        if (response.isSuccessful) {
            emit(response)
        }
    }
}
