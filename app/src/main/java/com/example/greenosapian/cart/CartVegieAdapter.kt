package com.example.greenosapian.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.databinding.ListItemCartBinding
import com.example.greenosapian.orderlist.VegieAdapter
import com.example.greenosapian.orderlist.VegieAdapter.Companion.KEY_IMAGE_URL
import com.example.greenosapian.orderlist.VegieAdapter.Companion.KEY_PRICE
import com.example.greenosapian.orderlist.VegieAdapter.Companion.KEY_QUANTITY
import com.example.greenosapian.orderlist.VegieAdapter.Companion.KEY_TOTAL_PRICE
import com.example.greenosapian.orderlist.VegieDiffCallback
import com.example.greenosapian.orderlist.VegieListener

class CartVegieAdapter(val clickListener: VegieListener) :
    ListAdapter<Vegie, CartVegieAdapter.ViewHolder>(VegieDiffCallback()) {


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

    class ViewHolder(val binding: ListItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
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
                        KEY_TOTAL_PRICE -> newVegie?.totalPrice = diffBundle.getLong(KEY_TOTAL_PRICE)
                    }
                }
                binding.veggie = newVegie
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCartBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


}