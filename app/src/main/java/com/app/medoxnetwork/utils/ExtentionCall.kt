package com.app.medoxnetwork.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.ImageView
import com.app.medoxnetwork.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(imageUrl: String) {
                Glide.with(this)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.gradient_play_wall)
                    .error(R.drawable.gradient_play_wall)
                    .into(this)
            }

fun ImageView.loadChatImage(imageUrl: String,w:Int=400,h:Int=400) {
    Glide.with(this)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(R.drawable.profile_placeholder)
        .override(w,h)
        .error(R.drawable.profile_placeholder)
        .into(this)
}

fun ImageView.loadGetAudioImage(imageUrl: String,w:Int=80,h:Int=80) {
    Glide.with(this)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(R.drawable.profile_placeholder)
        .override(w,h)
        .error(R.drawable.profile_placeholder)
        .into(this)
}


    fun ImageView.loadSlistImage(imageUrl: String) {
                Glide.with(this)
                .load(imageUrl)
               /* .diskCacheStrategy(DiskCacheStrategy.NONE)
                 .skipMemoryCache(true)*/
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(this)
    }
fun ImageView.setLocalImage(uri: Uri, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(uri)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}

    fun Context.getBitmap(name:String): Bitmap
    {
        val file = this.getFileStreamPath(name)
        val imagePath: String = file.getAbsolutePath()
        val bitmapImage = BitmapFactory.decodeFile(imagePath)
        val nh = (bitmapImage.height * (100.0 / bitmapImage.width)).toInt()
        val bit = Bitmap.createScaledBitmap(bitmapImage, 100, nh, true)
        return bit
    }

/*    fun Activity.buildMediaSource(uri: String): MediaSource {

       val pro= ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(Uri.parse(uri)))

    return ConcatenatingMediaSource(pro)
}*/

  inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
            SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
        }

  inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
            SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

