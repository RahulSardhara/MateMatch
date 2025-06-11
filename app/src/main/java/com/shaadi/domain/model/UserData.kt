package com.shaadi.domain.model

data class UserData(
    val fullName: String? = "",
    val dob: Dob? = Dob(),
    val email: String? = "",
    val gender: String? = "",
    val location: Location? = Location(),
    val login: Login? = Login(),
    val name: Name? = Name(), //We can also do same as location, but for simplicity, we keep it as a single object
    val phone: String? = "",
    val fullAddress: String? = "",// This field is not in the original JSON, added for compatibility
    val religion: String? = "", // This field is not in the original JSON, added for compatibility
    val caste: String? = "", // This field is not in the original JSON, added for compatibility
    val education: String? = "", // This field is not in the original JSON, added for compatibility
    val matchScore: Int? = 0, // This field is not in the original JSON, added for compatibility
    val status: String? = "",// This field is not in the original JSON, added for compatibility
    val picture: Picture? = Picture()
)

data class Dob(val age: Int? = 0, val date: String? = "")

data class Location(val city: String? = "", val country: String? = "", val state: String? = "", val postcode: String? = "")

data class Login(val uuid: String? = "")

data class Name(val first: String? = "", val last: String? = "", val title: String? = "")

data class Picture(val large: String? = "", val medium: String? = "", val thumbnail: String? = "")


