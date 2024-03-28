package com.example.thecocktailapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktailapp.model.CocktailResponse
import com.example.thecocktailapp.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(private val repository: CocktailRepository) :
    ViewModel() {

    private val _data = MutableLiveData<List<CocktailResponse.Drink>>()
    val data: LiveData<List<CocktailResponse.Drink>>
        get() = _data

    var queryString by mutableStateOf("")
        private set

    init {
        getCocktails(queryString)
    }

    fun getCocktails(input: String) {
        viewModelScope.launch {
            try {
                repository.getCocktailDrinksBasedOnQueryStr(input)
                    .collect {
                        if (it?.isNotEmpty() == true) {
                            _data.value = it
                        }
                    }

            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun updateDrinkQueryString(input: String?) {
        input?.let {
            queryString = it
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: List<CocktailResponse.Drink> = emptyList()
)
