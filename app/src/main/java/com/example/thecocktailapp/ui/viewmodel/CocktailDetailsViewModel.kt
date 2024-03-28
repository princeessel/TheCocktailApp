package com.example.thecocktailapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktailapp.data.model.CocktailResponse
import com.example.thecocktailapp.data.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailsViewModel @Inject constructor(
    private val repository: CocktailRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun getCocktailDetailsById(id: String) {
        viewModelScope.launch {
            val response = repository.getCocktailDetailsById(id)
            val drinks = response.firstOrNull().orEmpty()

            _uiState.update {
                it.copy(drink = drinks.firstOrNull())
            }
        }
    }

    fun mapIngredientAndMeasurement(drink: CocktailResponse.Drink): List<Pair<String?, String?>> {
        val ingredients = listOf(
            drink.strIngredient1,
            drink.strIngredient2,
            drink.strIngredient3,
            drink.strIngredient4,
            drink.strIngredient5,
            drink.strIngredient6,
            drink.strIngredient7,
            drink.strIngredient8,
            drink.strIngredient9,
            drink.strIngredient10,
            drink.strIngredient11,
            drink.strIngredient12,
            drink.strIngredient13,
            drink.strIngredient14,
            drink.strIngredient15
        )
        val measurement = listOf(
            drink.strMeasure1,
            drink.strMeasure2,
            drink.strMeasure3,
            drink.strMeasure4,
            drink.strMeasure5,
            drink.strMeasure6,
            drink.strMeasure7,
            drink.strMeasure8,
            drink.strMeasure9,
            drink.strMeasure10,
            drink.strMeasure11,
            drink.strMeasure12,
            drink.strMeasure13,
            drink.strMeasure14,
            drink.strMeasure15
        )

        return ingredients.filterNotNull().zip(measurement)
    }

    data class UiState(
        val loading: Boolean = false,
        val error: String? = null,
        val drink: CocktailResponse.Drink? = null
    )
//
//        private fun toggleFavIconOnClick(drink: Cocktail.Drink) {
//        isFavorite = if (isFavorite) {
//            binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
////            PreferenceManager.saveFavoriteCocktailsPref(this, drink = drink)
//            false
//        } else {
//            binding.icFav.setImageResource(R.drawable.ic_favorite)
////            PreferenceManager.removeFavoriteCocktailsPref(this, drinkId = drink.idDrink)
//            true
//        }
//    }
}
