package com.want.want.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.want.want.R

class MainActivity : AppCompatActivity() {

//    private val bottomNavigation: BottomNavigationView by lazy { findViewById(R.id.bottomNavigationView) }
//    private lateinit var navController: NavController
//    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //去掉title bar
//        this.supportActionBar?.hide()
//        navController = Navigation.findNavController(this,R.id.fragment)
//        appBarConfiguration = AppBarConfiguration.Builder(bottomNavigation.menu).build()
//        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
//        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}