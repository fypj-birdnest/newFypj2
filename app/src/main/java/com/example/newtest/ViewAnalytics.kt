package com.example.newtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_viewanalytics.*

class ViewAnalytics : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewanalytics)

        analytics_trends_cv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java) //yet to create page
            startActivity(intent)
        }

        customization_cv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java) //yet to create page
            startActivity(intent)
        }

    }
}