package com.example.thecocktailapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktailapp.model.Cocktail
import com.example.thecocktailapp.model.CocktailResponse
import com.example.thecocktailapp.model.toDomain
import com.example.thecocktailapp.repository.CocktailRepository
import com.example.thecocktailapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(private val repository: CocktailRepository) :
    ViewModel() {

    private val _data = MutableStateFlow<Resource<Cocktail>>(Resource.Loading())
    val data: StateFlow<Resource<Cocktail>>
        get() = _data.asStateFlow()

    var queryString by mutableStateOf("")
        private set

    fun getCocktails(input: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCocktailDrinks(input)
                response.collect { resource ->
                    _data.value = resource.data?.let { Resource.Success(it.toDomain()) }!!
                }

            } catch (e: Exception) {
                Resource.Error<CocktailResponse>("Error encountered: + ${e.message}")
            }
        }
    }

    fun updateDrinkQueryString(input: String?) {
        input?.let {
            queryString = it
        }
    }
}
