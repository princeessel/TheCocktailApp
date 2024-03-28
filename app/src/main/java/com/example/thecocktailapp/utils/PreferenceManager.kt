package com.example.thecocktailapp.utils

import android.content.Context
import com.example.thecocktailapp.data.model.Cocktail.Drink
import com.example.thecocktailapp.data.model.CocktailResponse
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder

object FavoritePreferenceManager {
    fun cacheCocktailsPref(context: Context, drinks: List<CocktailResponse.Drink>?) {
        val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE) ?: return

        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(drinks)

        with(sharedPref.edit()) {
            putString("FAV_DRINKS", jsonString)
        }
    }

    fun getFavoriteCocktailsPref(context: Context): List<CocktailResponse.Drink>? {
        val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE) ?: return mutableListOf()

        val json = sharedPref.getString("FAV_DRINKS", null)
        val typeToken = object : TypeToken<List<Drink>>() {}.type

        return GsonBuilder().create().fromJson(json, typeToken)
    }

//    fun removeFavoriteCocktailsPref(context: Context, drinkId: String) {
//        val drinks = getFavoriteCocktailsPref(context)
//        if (drinks.isEmpty()) return
//
//        drinks.forEach {
//            if (it.idDrink == drinkId) {
//                drinks.remove(it)
//            }
//        }
//
//    }
}
