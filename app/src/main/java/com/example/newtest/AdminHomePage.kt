package com.example.newtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adminhomepage.*

class AdminHomePage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminhomepage)

        edit_Form_cv.setOnClickListener{
            val intent = Intent(this, AdminScanResults::class.java)
            startActivity(intent)
        }

        batch_processing_cv.setOnClickListener {
            val intent = Intent(this, BatchProcessing::class.java)
            startActivity(intent)

        }
    }
}