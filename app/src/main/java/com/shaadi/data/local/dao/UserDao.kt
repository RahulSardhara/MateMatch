package com.shaadi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shaadi.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Update
    suspend fun update(user: UserEntity) : Int

    @Query("SELECT * FROM users where status = :status")
    fun getAll(status: String): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM users WHERE status IN (:statuses)")
    suspend fun getUsersByStatus(statuses: List<String>): List<UserEntity>


}