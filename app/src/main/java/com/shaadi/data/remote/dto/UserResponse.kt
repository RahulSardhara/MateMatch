package com.shaadi.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.shaadi.domain.model.UserData

data class UserResponse(val info: Info? = Info(), @SerializedName("results") val userData: List<UserData>? = listOf())

data class Info(val page: Int? = 0, val results: Int? = 0, val seed: String? = "", val version: String? = "")


