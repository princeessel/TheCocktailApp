package com.example.thecocktailapp.repository

import com.example.thecocktailapp.datasource.BaseApiResponse
import com.example.thecocktailapp.datasource.CocktailDataSourceImpl
import com.example.thecocktailapp.model.CocktailResponse
import com.example.thecocktailapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CocktailRepository @Inject constructor(private val cocktailDataSourceImpl: CocktailDataSourceImpl) :
    BaseApiResponse() {

    suspend fun getCocktailDrinks(s: String): Flow<Resource<CocktailResponse>> = flow {
        emit(execute { cocktailDataSourceImpl.getCocktailData(s) })
    }.flowOn(Dispatchers.IO)

    suspend fun getCocktailDetailsById(id: String): Flow<Resource<CocktailResponse>> = flow {
        emit(execute { cocktailDataSourceImpl.getCocktailDetailsById(id) })
    }.flowOn(Dispatchers.IO)

}
