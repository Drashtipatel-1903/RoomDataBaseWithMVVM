package com.example.roomdatabasedemo.viewmodals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabasedemo.modal.UserEntity
import com.example.roomdatabasedemo.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModal(private val userRepository: UserRepository) : ViewModel()
{
    fun insertUserData(userEntity: UserEntity) = viewModelScope.launch {
        userRepository.insertUser(userEntity)
    }

    fun getAllUserData() : LiveData<List<UserEntity>> = userRepository.getAllUserData()

    fun deleteUserData(userEntity: UserEntity) = viewModelScope.launch {
        userRepository.deleteUserData(userEntity)
    }

    fun updateUserData(userEntity: UserEntity) = viewModelScope.launch {
        userRepository.updateUserData(userEntity)
    }
}