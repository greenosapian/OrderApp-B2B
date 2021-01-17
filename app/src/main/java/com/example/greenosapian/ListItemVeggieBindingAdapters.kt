package com.example.greenosapian

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

@BindingAdapter("invisibleUnlessQuantity")
fun hideViewOnQuantity(view:View, quantity: Int){
    view.visibility = if (quantity != 0) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("visibleUnlessQuantity")
fun showViewOnQuantity(view:View, quantity: Int){
    view.visibility = if (quantity == 0) View.VISIBLE else View.INVISIBLE
}
