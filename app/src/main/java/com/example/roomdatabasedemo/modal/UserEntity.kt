package com.example.roomdatabasedemo.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)  val id : Int,
    val username : String? = null ,
    @ColumnInfo(name = "email_id")
    val email : String? = null ,
    val password : String? = null
)