package com.shaadi.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.shaadi.R

fun <T> Context.clearTaskAndOpenActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun ImageView.loadProfileImage(vararg urls: String?) {
    val validUrl = urls.firstOrNull { !it.isNullOrEmpty() }
    Glide.with(this.context)
        .load(validUrl)
        .placeholder(R.drawable.ic_placeholder)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment?.showSnackBar(message: String?) {
    try {
        this?.let {
            Snackbar.make(
                it.requireActivity().findViewById(android.R.id.content),
                message ?: "No error message attached",
                Snackbar.LENGTH_SHORT
            ).apply {
                (this.view).findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    ?.let { text ->
                        text.maxLines = 5
                    }
                show()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.toggleVisibility(): Int = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE


fun View?.setVisible(value: Int? = View.VISIBLE) {
    this?.visibility = value ?: View.VISIBLE
}

fun View?.isVisible(): Boolean {
    return this?.visibility == View.VISIBLE
}

fun View?.setInVisible(value: Int? = View.INVISIBLE) {
    this?.visibility = value ?: View.INVISIBLE
}

fun View?.setGone(value: Int? = View.GONE) {
    this?.visibility = value ?: View.GONE
}
