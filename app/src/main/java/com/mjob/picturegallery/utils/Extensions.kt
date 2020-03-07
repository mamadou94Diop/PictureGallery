package com.mjob.picturegallery.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mjob.picturegallery.R
import com.mjob.picturegallery.ui.MainActivity
import dagger.android.support.DaggerFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream


@GlideModule
@Excludes(
    OkHttpLibraryGlideModule::class)
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder() .addNetworkInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .header("User-Agent", "Dalvic/2.1.0 (Linux; U; Android 10; Pixel 3 XL Build/QP1A.191005.007)")
                    .build()
            )
        }.addInterceptor(interceptor).build()

        registry.replace(GlideUrl::class.java, InputStream::class.java,  OkHttpUrlLoader.Factory(client));
    }
}

fun ImageView.loadImageFromUrl(
    url: String,
    onSuccessfulImageLoadingCallback: (() -> Unit)? = null,
    onFailedImageLoadingCallback: (() -> Unit)? = null
) {
    GlideApp.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.no_image)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onFailedImageLoadingCallback?.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onSuccessfulImageLoadingCallback?.invoke()
                return false
            }
        })
        .into(this)
}

fun DaggerFragment.parentActivity(): MainActivity {
    return (activity as MainActivity)
}
