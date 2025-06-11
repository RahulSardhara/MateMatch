package com.shaadi.data.remote.network

import android.content.Context
import androidx.annotation.StringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

sealed class DataState<T> {

    data class Loading<T>(val isLoading: Boolean) : DataState<T>()
    data class Success<T>(val data: T) : DataState<T>()
    data class ResponseError<T>(val code: Int?, val message: UIText, val error: Any?) : DataState<T>()
    data class ExceptionError<T>(val message: UIText, val throwable: Throwable?) : DataState<T>()
}

sealed class UIComponent {

    data class Toast(val uiText: UIText) : UIComponent()
    data class SnackBar(val uiText: UIText) : UIComponent()
    data class Dialog(val title: UIText, val message: UIText) : UIComponent()

}

sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ) : UIText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}

fun String?.toUIText(): UIText = UIText.DynamicString(this ?: "")


val uiMessageChannel = Channel<UIComponent>()
val uiMessageEvents = uiMessageChannel.receiveAsFlow()

val uiProgressChannel = Channel<Pair<Boolean,Boolean>>()
val uiProgressEvents = uiProgressChannel.receiveAsFlow()