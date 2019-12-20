package com.example.newtest

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_analysis.*
import kotlinx.android.synthetic.main.activity_customization.*

class Customization : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customization)

        val adapterX = ArrayAdapter.createFromResource(this,
                R.array.x_list, android.R.layout.simple_spinner_item)

        adapterX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        customization_spinner_x.adapter = adapterX

        val adapterY = ArrayAdapter.createFromResource(this,
                R.array.y_list, android.R.layout.simple_spinner_item)

        adapterY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        customization_spinner_y.adapter = adapterY

    }

}