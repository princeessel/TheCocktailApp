package com.example.thecocktailapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.thecocktailapp.R
import com.example.thecocktailapp.ui.viewmodel.CocktailDetailsViewModel

@Composable
fun DetailsScreen(drinkId: String) {
    val viewModel: CocktailDetailsViewModel = hiltViewModel()
    viewModel.getCocktailDetailsById(drinkId)

    viewModel.uiState.collectAsStateWithLifecycle().let { state  ->
        val request = ImageRequest.Builder(LocalContext.current)
            .data(state.value.drink?.strDrinkThumb)
            .build()


        Column {
            state.value.drink?.let {
                val ingredientMap = viewModel.mapIngredientAndMeasurement(it)
                AsyncImage(
                    model = request,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .clip(RectangleShape)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = " ${it.strDrink}",
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = " ${it.dateModified ?: ""}",
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "Ingredients && Measurement",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
                ingredientMap.forEach {
                    Row {
                        Text(text = " ${it.first} : ")
                        Text(text = "${it.second}")
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Instructions to make ${it.strDrink}",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.padding(4.dp))

                Text(text = it.strInstructions!!)
            }
        }
    }
}
