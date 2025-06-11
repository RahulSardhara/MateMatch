package com.shaadi.data.remote.mapper

import com.shaadi.data.local.entity.UserEntity
import com.shaadi.domain.model.Dob
import com.shaadi.domain.model.Location
import com.shaadi.domain.model.Login
import com.shaadi.domain.model.Name
import com.shaadi.domain.model.Picture
import com.shaadi.domain.model.UserData
import java.util.UUID
import kotlin.math.abs

fun UserData.toUserEntity(): UserEntity {

    val fullName = "${name?.title ?: ""} ${name?.first ?: ""} ${name?.last ?: ""}".trim()
    val city = location?.city ?: ""
    val country = location?.country ?: ""
    val state = location?.state ?: ""
    val pinCode = location?.postcode ?: ""
    val age = dob?.age ?: 0
    val religion = generateFakeReligion()
    val caste = generateFakeCaste(religion)
    val education = generateFakeEducation(age)
    val matchScore = calculateMatchScore(age, city)

    return UserEntity(
        id = login?.uuid ?: UUID.randomUUID().toString(),
        fullName = fullName,
        age = age,
        city = city,
        fullAddress = "$city, $state, $pinCode, $country",
        state = state,
        pinCode = pinCode,
        country = country,
        imageUrl = picture?.large ?: picture?.medium ?: picture?.thumbnail ?: "",
        matchScore = matchScore,
        status = "",
        education = education,
        religion = religion,
        caste = caste
    )
}


fun UserEntity.toDomain(): UserData {
    return UserData(
        fullName = this.fullName,
        name = parseName(this.fullName),
        location = Location(city = this.city, country = this.country, state = this.state, postcode = this.pinCode),
        fullAddress = this.fullAddress,
        login = Login(uuid = this.id),
        dob = Dob(age = this.age),
        picture = Picture(large = this.imageUrl),
        religion = this.religion,
        caste = this.caste,
        education = this.education,
        matchScore = this.matchScore,
        status = this.status
    )
}

fun parseName(rawName: String): Name {
    val parts = rawName.trim().split(" ")
    val title = if (parts.size > 2) parts[0] else null
    val first = if (parts.size > 1) parts.getOrNull(1) ?: parts.getOrNull(0) else parts.getOrNull(0)
    val last = if (parts.size > 2) parts.drop(2).joinToString(" ") else parts.getOrNull(1)
    return Name(title = title, first = first, last = last)
}

fun calculateMatchScore(age: Int, city: String): Int {
    val myAge = 28
    val myCity = "Mumbai"
    val ageScore = (50 - abs(myAge - age) * 5).coerceAtLeast(0)
    val cityScore = if (city == myCity) 50 else 0
    return ageScore + cityScore
}

fun generateFakeEducation(age: Int?): String {
    return when {
        (age ?: 0) < 25 -> "Bachelor's"
        (age ?: 0) < 35 -> "Master's"
        else -> "Ph.D."
    }
}

fun generateFakeReligion(): String {
    val religions = listOf("Hindu", "Muslim", "Christian", "Sikh", "Jain")
    return religions.random()
}

fun generateFakeCaste(religion: String): String {
    return when (religion) {
        "Hindu" -> listOf("Brahmin", "Rajput", "Kayastha", "Yadav", "Maratha").random()
        "Muslim" -> listOf("Sunni", "Shia", "Pathan", "Syed", "Sheikh").random()
        "Christian" -> listOf("Roman Catholic", "Protestant", "Orthodox").random()
        "Sikh" -> listOf("Jat", "Ramgarhia", "Khatri", "Arora").random()
        "Jain" -> listOf("Digambar", "Shwetambar").random()
        else -> "General"
    }
}
