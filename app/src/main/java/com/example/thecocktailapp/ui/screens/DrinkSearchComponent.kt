package com.example.thecocktailapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.thecocktailapp.ui.viewmodel.CocktailViewModel

@Composable
fun DrinkSearchComponent(
    viewModel: CocktailViewModel = hiltViewModel()
) {
    var query = viewModel.queryString

    Row(horizontalArrangement = Arrangement.Center) {
        TextField(
            value = query,
            onValueChange = { onQueryChange ->
                query = onQueryChange
                viewModel.updateDrinkQueryString(onQueryChange)
            },
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(text = "Search drinks") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(16.dp)
                .width(250.dp)
        )

        Button(
            onClick = {
                if (query.isNotEmpty()) {
                    viewModel.getCocktails()
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0XFF0F9D58)),
            modifier = Modifier
                .padding(top = 16.dp, end = 8.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Submit",
                color = Color.White,
                maxLines = 1,
                textAlign = TextAlign.Left
            )
        }

    }
}