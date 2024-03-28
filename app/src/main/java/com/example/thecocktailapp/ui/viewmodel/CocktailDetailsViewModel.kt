package com.example.thecocktailapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecocktailapp.model.Cocktail
import com.example.thecocktailapp.model.Cocktail.Drink
import com.example.thecocktailapp.model.toDomain
import com.example.thecocktailapp.repository.CocktailRepository
import com.example.thecocktailapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailsViewModel @Inject constructor(private val repository: CocktailRepository) :
    ViewModel() {
    private val _drink = MutableLiveData<Drink>()
    val drink: LiveData<Drink> = _drink


    fun getCocktailDetailsById(id: String) {
        viewModelScope.launch {
            val response = repository.getCocktailDetailsById(id)
            response.collect {
                _drink.value = it?.get(0)?.toDomain()
            }
        }
    }

    fun mapIngredientAndMeasurement(drink: Drink): List<Pair<String?, String?>> {
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

        return ingredients.zip(measurement)
    }

}
