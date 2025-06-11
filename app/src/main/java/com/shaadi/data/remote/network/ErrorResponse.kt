package com.shaadi.data.remote.network

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.ResponseBody

data class ErrorResponse(
    @SerializedName("message", alternate = ["Message"])
    val message: String?,
    @SerializedName("status")
    val status: Boolean?, // false
)

fun parseError(errorBody: ResponseBody?): String {
    return try {
        errorBody?.string().toString().let {
            val data = GsonBuilder().serializeNulls()
                .create()
                .fromJson(it, ErrorResponse::class.java)
            data.message ?: "Something went wrong with that request"
        }
    } catch (e: Throwable) {
        "Something went wrong with that request"
    }
}
