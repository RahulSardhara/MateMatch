package com.shaadi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shaadi.utils.Constant.USER_TABLE

@Entity(tableName = USER_TABLE)
data class UserEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val age: Int,
    val city: String,
    val state: String,
    val pinCode: String,
    val country: String,
    val fullAddress: String,
    val imageUrl: String,
    val matchScore: Int,
    val status: String? = "Unknown", //Status can be "Accepted", "Rejected", or "Unknown"
    val education: String,//Extra field added for education matching
    val religion: String? = "", //Extra field added for religion matching
    val caste: String? = "", //Extra field added for caste matching
)

