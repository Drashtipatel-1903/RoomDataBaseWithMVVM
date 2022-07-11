package com.example.roomdatabasedemo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.roomdatabasedemo.R
import com.example.roomdatabasedemo.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)

        var toolbar:MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //findNavController(R.id.fragmentContainerView)
        var navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        var navController = navHost.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.splashFragment, R.id.signupFragment))
        setupActionBarWithNavController(navController , appBarConfiguration)


    }
}