package com.example.roomdatabasedemo.repository

import androidx.lifecycle.LiveData
import com.example.roomdatabasedemo.dao.UserDao
import com.example.roomdatabasedemo.modal.UserEntity

class UserRepository(private val userDao: UserDao)
{
    suspend fun insertUser(userEntity: UserEntity)
    {
        userDao.insertUserData(userEntity)
    }

    fun getAllUserData():LiveData<List<UserEntity>> = userDao.getAllUserData()

    suspend fun deleteUserData(userEntity: UserEntity)
    {
        userDao.deleteUserData(userEntity)
    }

    suspend fun updateUserData(userEntity: UserEntity)
    {
        userDao.updateUserData(userEntity)
    }
}