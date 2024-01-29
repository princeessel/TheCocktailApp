package com.example.thecocktailapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktailapp.model.Cocktail
import com.example.thecocktailapp.model.CocktailResponse
import com.example.thecocktailapp.model.toDomain
import com.example.thecocktailapp.repository.CocktailRepository
import com.example.thecocktailapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(private val repository: CocktailRepository) :
    ViewModel() {


    private val _data = MutableLiveData<Resource<Cocktail>>()
    val data: LiveData<Resource<Cocktail>> = _data

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error


    fun getCocktails(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCocktailDrinks(query)
                response.collect { resource ->
                    _data.value = resource.data?.let { Resource.Success(it.toDomain()) }
                }

            } catch (e: Exception) {
                Resource.Error<CocktailResponse>("Error encountered: + ${e.message}")
            }
        }
    }

}
