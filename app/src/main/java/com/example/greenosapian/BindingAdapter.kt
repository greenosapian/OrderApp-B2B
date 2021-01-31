package com.example.greenosapian

import android.annotation.SuppressLint
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.orderlist.ElasticApiStatus
import java.lang.StringBuilder
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
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imgView)
    }
}

@BindingAdapter("imageInternetUrl")
fun bindImageFromInternet(imgView: ImageView, imgUrl:String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(it)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("formatTime")
fun formatDateFromTimestamp(textView: TextView, timeStamp:Long){
    textView.text = java.text.SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(Date(timeStamp))

}

@BindingAdapter("elasticApiStatus")
fun bindstatus(statusImageView: ImageView, status:ElasticApiStatus?){
    when(status) {
        ElasticApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ElasticApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("elasticApiStatus")
fun bindStatus(view: View, status:ElasticApiStatus?){
    when(status) {
        ElasticApiStatus.LOADING -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(recyclerView: RecyclerView, status:ElasticApiStatus?){
    when(status) {
        ElasticApiStatus.DONE -> recyclerView.visibility = View.VISIBLE
        else -> recyclerView.visibility = View.GONE
    }
}

