package com.example.thecocktailapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecocktailapp.databinding.ActivityMainBinding
import com.example.thecocktailapp.model.CocktailResponse
import com.example.thecocktailapp.utils.Resource
import com.example.thecocktailapp.viewmodel.CocktailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CocktailsAdapter.ItemClickListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CocktailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            val queryString = binding.search.text.toString()
            initAdapter()
            initViewModel(queryString)
        }

    }

    private fun initViewModel(queryString: String) {
        viewModel.getCocktails(queryString)
    }

    private fun initAdapter() {
        viewModel.data.observe(this) { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let {
                        val cocktailsAdapter = CocktailsAdapter(it.drinks, this)
                        binding.drinksRecycleview.adapter = cocktailsAdapter
                        binding.drinksRecycleview.layoutManager = LinearLayoutManager(this)
                    }
                }
                is Resource.Error -> Resource.Error<CocktailResponse>("")

                is Resource.Loading -> {}
                else -> {}
            }

        }
    }

    override fun onItemClick(position: Int, drinkId: String?) {
        val extras = Bundle()
        extras.putString("DRINK_ID", drinkId)
        val intent = Intent(this@MainActivity, CocktailDetailsActivity::class.java)
        intent.putExtras(extras)
        startActivity(intent)
    }
}