package com.tadiuzzz.forecast.feature.changecity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tadiuzzz.forecast.databinding.ItemCityBinding

class CityAdapter : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    companion object {
        interface OnItemClickListener {
            fun onItemClick(item: CityItem)
        }
    }

    private var founds: MutableList<CityItem> = ArrayList<CityItem>()
    private lateinit var onItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setItems(founds: List<CityItem>) {
        this.founds.clear()
        this.founds.addAll(founds)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        return CityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return founds.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(founds[position])
    }

    inner class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CityItem) {
            binding.item = item
            binding.listener = onItemClickListener
            binding.executePendingBindings()
        }
    }
}