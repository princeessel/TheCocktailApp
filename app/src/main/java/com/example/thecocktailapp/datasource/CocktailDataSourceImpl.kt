package com.example.thecocktailapp.datasource

import com.example.thecocktailapp.model.CocktailResponse
import retrofit2.Response
import javax.inject.Inject

class CocktailDataSourceImpl @Inject constructor(
    private val dataSource: CocktailDataSource
) : CocktailDataSource {
    override suspend fun getCocktailData(s: String): Response<CocktailResponse> = dataSource.getCocktailData(s)

    override suspend fun getCocktailDetailsById(i: String): Response<CocktailResponse> = dataSource.getCocktailDetailsById(i)
}