package com.example.thecocktailapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.thecocktailapp.databinding.CocktailRowItemBinding
import com.example.thecocktailapp.model.Cocktail.Drink

class CocktailsAdapter(private val drinks: List<Drink>, private val listener: ItemClickListener) :
    RecyclerView.Adapter<CocktailsAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(position: Int, drinkId: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CocktailRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drink = drinks[position]
        holder.bind(drink)
    }

    override fun getItemCount(): Int = drinks.size

    inner class ViewHolder(private val itemBinding: CocktailRowItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(drink: Drink) {
            itemBinding.root.setOnClickListener {
                if (bindingAdapterPosition >= 0) {
                    listener.onItemClick(bindingAdapterPosition, drink.idDrink)
                }
            }
            itemBinding.drinkName.text = drink.strDrink

            Glide.with(itemView.context)
                .load(drink.strDrinkThumb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemBinding.imageView)
        }
    }
}
