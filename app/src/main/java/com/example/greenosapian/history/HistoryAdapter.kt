package com.example.greenosapian.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenosapian.database.CartHistoryEntity
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.databinding.ListItemHistoryBinding
import com.example.greenosapian.orderlist.VegieAdapter
import com.example.greenosapian.orderlist.VegieListener

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
            setupInnerRecyclerView(binding, item.itemList)
            binding.executePendingBindings()
        }

        private fun setupInnerRecyclerView(
            binding: ListItemHistoryBinding,
            veggieList: List<Vegie>
        ) {
            val adapter = HistoryInnerAdapter()
            binding.recyclerView.adapter = adapter
            adapter.data = veggieList
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