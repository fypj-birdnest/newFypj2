package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_analysis.*
import kotlinx.android.synthetic.main.activity_customization.*

class Customization : AppCompatActivity(){

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
        setContentView(R.layout.activity_customization)

//        val adapterX = ArrayAdapter.createFromResource(this,
//                R.array.x_list, android.R.layout.simple_spinner_item)
//
//        adapterX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        customization_spinner_x.adapter = adapterX

        //spinner for y axis
        val adapterY = ArrayAdapter.createFromResource(this,
                R.array.y_list, android.R.layout.simple_spinner_item)

        adapterY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        customization_spinner_y.adapter = adapterY

        //spinner for month start
        val adapterMonth = ArrayAdapter.createFromResource(this,
            R.array.month_list, android.R.layout.simple_spinner_item)

        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        month_spinner_start.adapter = adapterMonth
        month_spinner_end.adapter = adapterMonth

        //spinner for year start
        val adapterYear = ArrayAdapter.createFromResource(this,
            R.array.year_list, android.R.layout.simple_spinner_item)

        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        year_spinner_start.adapter = adapterYear
        year_spinner_end.adapter = adapterYear

        }

    }

//    fun showHide(layout_date:LinearLayout){  //If date option is clicked, show the linear layout
//        layout_date.visibility = if (layout_date.visibility == LinearLayout.VISIBLE)
//        {
//            LinearLayout.INVISIBLE
//        }
//
//        else
//        {
//            LinearLayout.VISIBLE
//        }
//    }

