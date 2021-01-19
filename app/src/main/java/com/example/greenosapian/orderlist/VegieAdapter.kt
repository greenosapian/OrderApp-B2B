package com.example.greenosapian.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.databinding.ListItemVegieBinding

class VegieAdapter(val clickListener: VegieListener) :
    ListAdapter<Vegie, VegieAdapter.ViewHolder>(VegieDiffCallback()) {


    fun getItemFromAdapter(position: Int):Vegie? = getItem(position)

    //how to create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = getItem(position)
        holder.bind(item, clickListener, payloads)
    }
//how to use
//    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any?>) {
//        val item = getItem(position)
//        holder.bind(item, clickListener, payloads)
//    }

    class ViewHolder(val binding: ListItemVegieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Vegie,
            clickListener: VegieListener,
            payloads: MutableList<Any>? = null
        ) {

            if(payloads.isNullOrEmpty()){
                binding.veggie = item
                binding.clickListener = clickListener
            } else{
                val diffBundle = payloads.get(0) as Bundle
                val newVegie = binding.veggie?.copy()

                for(key:String in diffBundle.keySet()){
                    when(key){
                        KEY_QUANTITY -> newVegie?.quantity = diffBundle.getInt(KEY_QUANTITY)
                        KEY_PRICE -> newVegie?.price = diffBundle.getLong(KEY_PRICE)
                        KEY_IMAGE_URL -> newVegie?.imageUrl = diffBundle.getString(KEY_IMAGE_URL)
                    }
                }
                binding.veggie = newVegie
            }
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

    companion object{
        const val KEY_PRICE = "key_price"
        const val KEY_QUANTITY = "key_quantity"
        const val KEY_IMAGE_URL = "key_image_url"
    }
}


class VegieDiffCallback : DiffUtil.ItemCallback<Vegie>() {
    override fun areItemsTheSame(oldItem: Vegie, newItem: Vegie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vegie, newItem: Vegie): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Vegie, newItem: Vegie): Any? {

        val diffBundle = Bundle()
        if(oldItem.price != newItem.price){
            diffBundle.putLong(VegieAdapter.KEY_PRICE, newItem.price)
        }
        if(oldItem.quantity != newItem.quantity){
            diffBundle.putInt(VegieAdapter.KEY_QUANTITY, newItem.quantity)
        }
        if(oldItem.imageUrl != newItem.imageUrl){
            diffBundle.putString(VegieAdapter.KEY_IMAGE_URL, newItem.imageUrl)
        }

        if(diffBundle.size() == 0) return null
        return diffBundle
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