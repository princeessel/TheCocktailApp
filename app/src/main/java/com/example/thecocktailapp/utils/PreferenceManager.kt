package com.example.thecocktailapp.utils

import android.content.Context
import com.example.thecocktailapp.data.model.CocktailResponse
import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder

object PreferenceManager {
    fun cacheCocktailsPref(context: Context, drinks: List<CocktailResponse.Drink>?) {
        val sharedPref = context.getSharedPreferences("prefs", Context.MODE_PRIVATE) ?: return

        val jsonString = GsonBuilder().create().toJson(drinks)

        with(sharedPref.edit()) {
            putString("FAV_DRINKS", jsonString)
        }.apply()
    }

    fun getFavoriteCocktailsPref(context: Context): List<CocktailResponse.Drink>? {
        val sharedPref =
            context.getSharedPreferences("prefs", Context.MODE_PRIVATE) ?: return mutableListOf()

        val json = sharedPref.getString("FAV_DRINKS", null)
        val typeToken = object : TypeToken<List<CocktailResponse.Drink>>() {}.type

        return GsonBuilder().create().fromJson(json, typeToken)
    }
}
