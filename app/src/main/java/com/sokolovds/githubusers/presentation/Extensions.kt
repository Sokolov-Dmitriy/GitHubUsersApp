package com.sokolovds.githubusers.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.sokolovds.githubusers.R

fun String.setInView(
    group: Group,
    textView: TextView,
    text: String = this,
    visible: Boolean = true
) {
    if (this.isNotBlank()) {
        group.isVisible = visible
        textView.text = text
    }
}

fun ImageView.loadImage(
    url: String,
    @DrawableRes loadImage: Int = R.drawable.ic_default_avatar,
    @DrawableRes errorImage: Int = R.drawable.ic_default_avatar,
    circle: Boolean = false
) {
    val glideBuilder = Glide.with(context)
        .load(url)
        .placeholder(loadImage)
        .error(errorImage)
    if (circle) glideBuilder.circleCrop()
    glideBuilder.into(this)
}

fun <T> T?.chekNull(default: T) = this ?: default