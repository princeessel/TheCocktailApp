package com.example.thecocktailapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.thecocktailapp.R
import com.example.thecocktailapp.databinding.ActivityCocktailDetailsBinding
import com.example.thecocktailapp.model.Cocktail
import com.example.thecocktailapp.utils.FavoritePreferenceManager
import com.example.thecocktailapp.utils.Resource
import com.example.thecocktailapp.viewmodel.CocktailDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocktailDetailsBinding
    private val viewModel: CocktailDetailsViewModel by viewModels()

    private var isFavorite = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCocktailDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drinkId = checkNotNull(intent.extras?.getString("DRINK_ID"))

        initViewModel(id = drinkId)
        initObservers()
    }

    private fun initViewModel(id: String) {
        viewModel.getCocktailDetailsById(id)
    }

    private fun initObservers() {

        viewModel.drink.observe(this@CocktailDetailsActivity) { state ->
            when (state) {
                is Resource.Success -> {
                    val drink = state.data?.drinks?.firstOrNull()
                    val dataMap = drink?.let { viewModel.mapIngredientAndMeasurement(it) }
                    Glide.with(this@CocktailDetailsActivity)
                        .load(drink?.strDrinkThumb)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.cocktailImage)

                    val (ingredient, measurement) = checkNotNull(dataMap?.first())
                    binding.ingredientsValue.text = ingredient
                    binding.measurementValue.text = measurement

                    binding.instructions.text = drink?.strInstructions
                    binding.icFav.isVisible = true

                    binding.icFav.setOnClickListener {
                        toggleFavIconOnClick(checkNotNull(drink))
                    }
                }

                is Resource.Error -> {
                    binding.icFav.isVisible = false
                    Resource.Error<Cocktail>("")
                }

                is Resource.Loading -> {

                }
            }

        }
    }

    private fun toggleFavIconOnClick(drink: Cocktail.Drink) {
        isFavorite = if (isFavorite) {
            binding.icFav.setImageResource(R.drawable.ic_favorite_filled)
            FavoritePreferenceManager.saveFavoriteCocktailsPref(this, drink = drink)
            false
        } else {
            binding.icFav.setImageResource(R.drawable.ic_favorite)
            FavoritePreferenceManager.removeFavoriteCocktailsPref(this, drinkId = drink.idDrink)
            true
        }
    }
}