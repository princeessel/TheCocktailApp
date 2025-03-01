package com.example.thecocktailapp.data.datasource

import com.example.thecocktailapp.data.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailDataApi {

    @GET("search.php")
    suspend fun getCocktailData(
        @Query("s") s: String
    ): Response<CocktailResponse>


    @GET("lookup.php")
    suspend fun getCocktailDetailsById(
        @Query("i") i: String
    ): Response<CocktailResponse>


    @GET("hey.php")
    suspend fun getCocktailHey(
        @Query("s") i: String
    ): Response<CocktailResponse>
}
