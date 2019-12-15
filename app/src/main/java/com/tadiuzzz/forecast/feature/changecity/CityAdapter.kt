package com.tadiuzzz.forecast.feature.changecity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tadiuzzz.forecast.data.CityItem
import com.tadiuzzz.forecast.databinding.ItemCityBinding

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var items: MutableList<CityItem> = ArrayList<CityItem>()

    fun setItems(items: List<CityItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater)
        return CityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CityItem) {
            binding.data = item
            //TODO: add click listener
            binding.executePendingBindings()
        }
    }
}