package com.example.greenosapian.orderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.databinding.ListItemVegieBinding

class VegieAdapter(val clickListener: VegieListener) :
    ListAdapter<Vegie, VegieAdapter.ViewHolder>(VegieDiffCallback()) {

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
        fun bind(item: Vegie, clickListener: VegieListener) {
            binding.veggie = item
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

class VegieDiffCallback : DiffUtil.ItemCallback<Vegie>() {
    override fun areItemsTheSame(oldItem: Vegie, newItem: Vegie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vegie, newItem: Vegie): Boolean {
        return oldItem == newItem
    }
}

class VegieListener(val addButtonListener: (vegie: Vegie) -> Unit,
                    val removeButtonlistener: (vegie: Vegie) -> Unit,
                    val changeQuantity: (vegie: Vegie, stepValue:Int) -> Unit
) {
    fun onAddClicked(vegie: Vegie) = addButtonListener(vegie)
    fun onRemovedClick(vegie: Vegie) = removeButtonlistener(vegie)
    fun increaseQuantity(vegie: Vegie) = changeQuantity(vegie, 1)
    fun decreaseQuantity(vegie: Vegie) = changeQuantity(vegie, -1)
}