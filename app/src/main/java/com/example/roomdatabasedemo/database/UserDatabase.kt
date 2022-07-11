package com.example.roomdatabasedemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabasedemo.dao.UserDao
import com.example.roomdatabasedemo.modal.UserEntity

//@Database(entities = [UserEntity::class], version = 1)
//abstract class UserDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}

@Database(entities = [UserEntity::class] , version = 2)
abstract class UserDatabase : RoomDatabase()
{
    abstract fun userDao() : UserDao

    companion object
    {
        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getInstance(context: Context):UserDatabase
        {
            synchronized(this)
            {
                var instance = INSTANCE
                if(instance==null)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user-database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}