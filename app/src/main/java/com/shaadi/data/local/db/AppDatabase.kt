package com.shaadi.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shaadi.data.local.dao.UserDao
import com.shaadi.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
