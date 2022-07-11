package com.example.roomdatabasedemo.viewmodals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabasedemo.repository.UserRepository

class UserViewModalFactory(private val userRepository: UserRepository) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModal(userRepository) as T
    }
}