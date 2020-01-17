package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.newtest.Class.QrString
import kotlinx.android.synthetic.main.activity_qr_finished.*
import kotlinx.android.synthetic.main.activity_results.*
import kotlinx.android.synthetic.main.activity_results.acidityLevelValue
import kotlinx.android.synthetic.main.activity_results.brandValue
import kotlinx.android.synthetic.main.activity_results.countryOrigin

class QrFinished : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_finished)

        var qrClass = intent.getSerializableExtra("qrClass") as? QrString
//        brandValue.text = qrClass?.brand
//        theCountry.text = qrClass?.country
        acidityLevelValue.text = qrClass?.acid
        collagenLevel.text = qrClass?.collagen
        salivaLevel.text = qrClass?.saliva

        //Log.d("qrclassR",qrClass?.brand!!)

        val backButton = findViewById<Button>(R.id.backDetails)

        backButton.setOnClickListener{
            val intent = Intent(this, BatchDetails::class.java)
            startActivity(intent)
        }

    }
}