package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_homepage.*

class HomePage : AppCompatActivity(){

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.authenticate_EBN -> {
                authenticate_EBN()
                true
            }
            R.id.EBN_analytics -> {
                EBN_analytics()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun authenticate_EBN(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun EBN_analytics(){
        val intent = Intent(this, ViewAnalytics::class.java)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        authenticate_EBN_cv.setOnClickListener{
            val intent = Intent(this, QrConsumer::class.java)
            startActivity(intent)
        }

        view_EBN_analytics_cv.setOnClickListener{
            val intent = Intent(this, ViewAnalytics::class.java)
            startActivity(intent)
        }

        val clickHere = findViewById<TextView>(R.id.adminClick)
        clickHere.setOnClickListener {
            val intent = Intent(this, BatchProcessing::class.java) //yet to create admin page
            startActivity(intent)

        }

    }
}