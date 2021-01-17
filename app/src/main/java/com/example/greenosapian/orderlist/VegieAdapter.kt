package com.example.greenosapian.orderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenosapian.databinding.ListItemVegieBinding
import com.example.greenosapian.network.NetworkVegie

class VegieAdapter(val clickListener: VegieListener) :
    ListAdapter<NetworkVegie, VegieAdapter.ViewHolder>(VegieDiffCallback()) {

    //how to create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //how to use
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder(val binding: ListItemVegieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NetworkVegie, clickListener: VegieListener) {
            binding.networkVegie = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemVegieBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class VegieDiffCallback : DiffUtil.ItemCallback<NetworkVegie>() {
    override fun areItemsTheSame(oldItem: NetworkVegie, newItem: NetworkVegie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NetworkVegie, newItem: NetworkVegie): Boolean {
        return oldItem == newItem
    }
}

class VegieListener(val clickListener: (vegie: NetworkVegie) -> Unit) {
    fun onClick(networkVegie: NetworkVegie) = clickListener(networkVegie)
}