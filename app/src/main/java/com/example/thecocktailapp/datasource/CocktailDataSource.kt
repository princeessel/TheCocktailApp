package com.example.thecocktailapp.datasource

import com.example.thecocktailapp.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailDataSource {

    @GET("search.php")
    suspend fun getCocktailData(
        @Query("s") s: String
    ): Response<CocktailResponse>


    @GET("lookup.php")
    suspend fun getCocktailDetailsById(
        @Query("i") i: String
    ): Response<CocktailResponse>
}
