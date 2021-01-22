package com.example.greenosapian.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenosapian.database.CartHistoryEntity
import com.example.greenosapian.databinding.ListItemHistoryBinding

class HistoryAdapter(private val clickListener: HistoryListener) :
    ListAdapter<CartHistoryEntity, HistoryAdapter.ViewHolder>(HistoryDiffCallback()) {

    //how to create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //how to show
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder(val binding: ListItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: CartHistoryEntity, clickListener: HistoryListener
        ) {
            binding.historyItem = item
            binding.historyListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemHistoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class HistoryDiffCallback : DiffUtil.ItemCallback<CartHistoryEntity>() {
    override fun areItemsTheSame(oldItem: CartHistoryEntity, newItem: CartHistoryEntity): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: CartHistoryEntity, newItem: CartHistoryEntity): Boolean {
        return oldItem.time == newItem.time && oldItem.totalPrice == newItem.totalPrice
    }
}

interface HistoryListener {
}