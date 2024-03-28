package com.example.thecocktailapp.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktailapp.data.datasource.LocalCocktailDataSource
import com.example.thecocktailapp.data.model.CocktailResponse
import com.example.thecocktailapp.data.repository.CocktailRepository
import com.example.thecocktailapp.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val repository: CocktailRepository,
    private val app: Application
) : ViewModel() {

    private val _data = MutableStateFlow(UiState())
    val data: StateFlow<UiState>
        get() = _data

    var queryString by mutableStateOf("")
        private set

    fun getCocktails() {
        viewModelScope.launch {
            val input = queryString
            try {
                val response = repository.getCocktailDrinksBasedOnQueryStr(input)
                val drinks = response.firstOrNull()
                _data.update {
                    it.copy(drinks = drinks.orEmpty())
                }
            } catch (e: Exception) {
                _data.update { it.copy(error = e.message) }
            }

            val filteredCachedData = repository.getCachedCocktailData(input).firstOrNull().orEmpty()
            if (filteredCachedData.isNotEmpty()) {
                _data.update {
                    it.copy(drinks = filteredCachedData)
                }
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
    val drinks: List<CocktailResponse.Drink> = emptyList()
)
