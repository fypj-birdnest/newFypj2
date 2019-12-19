package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_homepage.*

class HomePage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        authenticate_EBN_cv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        view_EBN_analytics_cv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java) //yet to create page
            startActivity(intent)
        }

        val clickHere = findViewById<TextView>(R.id.adminClick)
        clickHere.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) //yet to create page
            startActivity(intent)

        }

    }
}