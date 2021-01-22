package com.example.greenosapian

import android.annotation.SuppressLint
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.DateFormat
import java.util.*


@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible:Boolean){
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:invisibleUnless")
fun invisibleUnless(view: View, visible:Boolean){
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("imageUri")
fun bindImage(imgView: ImageView, imgUrl: Uri?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .apply(RequestOptions.circleCropTransform())
            .into(imgView)
    }
}

@BindingAdapter("imageInternetUrl")
fun bindImageFromInternet(imgView: ImageView, imgUrl:String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(it)
//            .apply(RequestOptions()
////                .placeholder(R.drawable.loading_animation)
//                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("formatTime")
fun formatDateFromTimestamp(textView: TextView, timeStamp:Long){
    textView.text = java.text.SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(Date(timeStamp))

}

