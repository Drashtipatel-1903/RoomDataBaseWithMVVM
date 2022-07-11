package com.example.roomdatabasedemo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabasedemo.modal.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao
{
    @Insert
    suspend fun insertUserData(userEntity: UserEntity)

    @Query("SELECT * FROM `user_table`")
    fun getAllUserData():LiveData<List<UserEntity>>

    @Delete
    suspend fun deleteUserData(userEntity: UserEntity)

    @Update
    suspend fun updateUserData(userEntity: UserEntity)


}