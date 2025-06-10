package com.shaadi.data.remote.dto

import android.graphics.Picture

data class UserResponse(
    val info: Info? = Info(),
    val results: List<Result?>? = listOf()
)

data class Info(
    val page: Int? = 0,
    val results: Int? = 0,
    val seed: String? = "",
    val version: String? = ""
)
data class Result(
    val cell: String? = "",
    val dob: Dob? = Dob(),
    val email: String? = "",
    val gender: String? = "",
    val id: Id? = Id(),
    val location: Location? = Location(),
    val login: Login? = Login(),
    val name: Name? = Name(),
    val nat: String? = "",
    val phone: String? = "",
    val religion: String? = "", //Extra field added for religion matching
    val caste: String? = "", //Extra field added for caste matching
    val occupations: String? = "", //Extra field added for occupations
    val picture: Picture? = Picture(),
    val registered: Registered? = Registered()
)

data class Dob(
    val age: Int? = 0,
    val date: String? = ""
)

data class Id(
    val name: String? = "",
    val value: String? = ""
)

data class Location(
    val city: String? = "",
    val coordinates: Coordinates? = Coordinates(),
    val country: String? = "",
    val postcode: Int? = 0,
    val state: String? = "",
    val street: Street? = Street(),
    val timezone: Timezone? = Timezone()
)

data class Coordinates(
    val latitude: String? = "",
    val longitude: String? = ""
)
data class Street(
    val name: String? = "",
    val number: Int? = 0
)

data class Timezone(
    val description: String? = "",
    val offset: String? = ""
)

data class Login(
    val md5: String? = "",
    val password: String? = "",
    val salt: String? = "",
    val sha1: String? = "",
    val sha256: String? = "",
    val username: String? = "",
    val uuid: String? = ""
)


data class Name(
    val first: String? = "",
    val last: String? = "",
    val title: String? = ""
)

data class Picture(
    val large: String? = "",
    val medium: String? = "",
    val thumbnail: String? = ""
)

data class Registered(
    val age: Int? = 0,
    val date: String? = ""
)