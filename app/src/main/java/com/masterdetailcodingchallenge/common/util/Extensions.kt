package com.masterdetailcodingchallenge.common.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.masterdetailcodingchallenge.R

inline fun View.showSnackBar(
    message: String,
    hasAction: Boolean = false,
    actionText: String? = null,
    crossinline action: () -> Unit = {},
    duration: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(
        this,
        message,
        duration
    ).apply {
        if (hasAction) {
            setAction(actionText) {
                action()
            }
        }
    }.show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ImageView.centerCrop(imageStringUrl: String) {
    Glide
        .with(context)
        .load(imageStringUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_error_placeholder_24)
        .into(this);
}
