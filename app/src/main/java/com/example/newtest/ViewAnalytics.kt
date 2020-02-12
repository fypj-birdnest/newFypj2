package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_viewanalytics.*

class ViewAnalytics : AppCompatActivity(){

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.bottom_nav_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle item selection
//        return when (item.itemId) {
//            R.id.authenticate_EBN -> {
//                authenticate_EBN()
//                true
//            }
//            R.id.EBN_analytics -> {
//                EBN_analytics()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    fun authenticate_EBN(){
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//
//    }
//
//    fun EBN_analytics(){
//        val intent = Intent(this, ViewAnalytics::class.java)
//        startActivity(intent)
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewanalytics)

        analytics_trends_cv.setOnClickListener{
            val intent = Intent(this, Statistics::class.java) //goes to the dashboard
            startActivity(intent)
        }

        customization_cv.setOnClickListener{
            val intent = Intent(this, Customization::class.java) //goes to customization page
            startActivity(intent)
        }
    }
}