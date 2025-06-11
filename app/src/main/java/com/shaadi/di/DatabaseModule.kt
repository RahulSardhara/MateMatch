package com.shaadi.di

import android.content.Context
import androidx.room.Room
import com.shaadi.data.local.dao.UserDao
import com.shaadi.data.local.db.AppDatabase
import com.shaadi.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constant.DATABASE_NAME
        ).build()
    }
}