package com.example.thecocktailapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thecocktailapp.data.model.Cocktail
import com.example.thecocktailapp.data.model.toDomain
import com.example.thecocktailapp.ui.viewmodel.CocktailViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thecocktailapp.R

@Composable
fun HomeScreen(onNavigateToDetailsScreen: (drink: Cocktail.Drink) -> Unit) {
    val viewModel: CocktailViewModel = hiltViewModel()
    val uiState by viewModel.data.collectAsStateWithLifecycle()

    Column {
        DrinkSearchComponent()
        LazyColumn {
            if (uiState.drinks.isNotEmpty()) {
                items(uiState.drinks) {
                    CocktailCard(drink = it.toDomain()) {
                        onNavigateToDetailsScreen(it.toDomain())
                    }
                }
            }

        }
    }
}

@Composable
fun CocktailCard(drink: Cocktail.Drink, onNavigateToDetailsScreen: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
            .clickable { onNavigateToDetailsScreen() }
    ) {
        val request = ImageRequest.Builder(LocalContext.current)
            .data(drink.strDrinkThumb)
            .build()

        Row {
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                model = request,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier
                    .clip(RectangleShape)
                    .width(100.dp)
                    .padding(end = 16.dp)
            )
            Column {
                drink.strDrink?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                drink.strCategory?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }
        }
    }
}
