package com.example.newtest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adminselection.*

class AdminSelection : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminselection)

        button_scan_results.setOnClickListener {
            val intent = Intent(this, AdminScanResults::class.java)
            startActivity(intent)
        }
    }
}