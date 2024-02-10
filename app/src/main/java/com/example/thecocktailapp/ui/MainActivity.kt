package com.example.thecocktailapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.TheCocktailAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.thecocktailapp.viewmodel.CocktailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thecocktailapp.model.Cocktail
import com.example.thecocktailapp.utils.Resource

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheCocktailAppTheme {
                Column {
                    DrinkTextField()
                }

            }
        }
    }

    @Composable
    fun DrinksList(viewModel: CocktailViewModel = viewModel()) {

        val data = viewModel.data.collectAsState().value
        LazyColumn {
            when (data) {
                is Resource.Success -> {
                    data.data?.drinks?.let {
                        items(it) { drink ->
                            CocktailCard(drink)
                        }
                    }
                }

                is Resource.Error -> {}
                else -> {}
            }
        }

    }

    @Composable
    fun DrinkTextField(
        viewModel: CocktailViewModel = viewModel(),
    ) {
        var query = viewModel.queryString

        Column {
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
                            viewModel.getCocktails(query)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "TextField is Empty",
                                Toast.LENGTH_LONG
                            ).show()
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
            DrinksList()

        }
    }

    @Composable
    fun CocktailCard(drink: Cocktail.Drink) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(all = 12.dp)
        ) {
            Row(modifier = Modifier.padding(all = 8.dp)) {
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.padding()) {
                    drink.strDrink?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
                        drink.dateModified?.let {
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
    }

    @Preview
    @Composable
    fun PreviewDrinkTextField() {
        TheCocktailAppTheme {
            Surface {
                DrinkTextField()
            }
        }
    }
}
