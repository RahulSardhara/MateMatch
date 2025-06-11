package com.shaadi.di

import com.shaadi.data.local.dao.UserDao
import com.shaadi.data.local.db.AppDatabase
import com.shaadi.data.remote.api.NetworkService
import com.shaadi.data.repository.UserRepositoryImpl
import com.shaadi.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesUserRepository(
        networkService: NetworkService,
        db: AppDatabase
    ): UserRepository = UserRepositoryImpl(networkService = networkService, db = db)
}