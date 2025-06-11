package com.shaadi.core

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class CoreActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val layoutResId: Int,
) : AppCompatActivity() {

    private var _binding: T? = null
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lockOrientation()
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        window?.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}

@SuppressLint("SourceLockedOrientationActivity")
fun AppCompatActivity.lockOrientation(orientation: Int = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
    requestedOrientation = orientation
}