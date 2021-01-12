package com.example.greenosapian

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible:Boolean){
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:invisibleUnless")
fun invisibleUnless(view: View, visible:Boolean){
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: Uri?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .apply(RequestOptions.circleCropTransform())
            .into(imgView)
    }
}