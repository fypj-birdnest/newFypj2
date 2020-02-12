package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_adminhomepage.*

class AdminHomePage : AppCompatActivity(){

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                authenticate_EBN()
                true
            }
//            R.id.EBN_analytics -> {
//                EBN_analytics()
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun authenticate_EBN(){
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)

    }


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