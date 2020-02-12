package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.newtest.Class.QrString
//import com.google.zxing.qrcode.encoder.QRCode
import kotlinx.android.synthetic.main.activity_qr_finished.*
import kotlinx.android.synthetic.main.activity_results.*

import kotlinx.android.synthetic.main.activity_results.brandValue
import kotlinx.android.synthetic.main.activity_results.countryOrigin
import net.glxn.qrgen.android.QRCode

class QrFinished : AppCompatActivity(){
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
//        val intent = Intent(this, HomePage::class.java)
//        startActivity(intent)
//
//    }
//
//    fun EBN_analytics(){
//        val intent = Intent(this, HomePage::class.java)
//        startActivity(intent)
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_finished)

        var qrClass = intent.getSerializableExtra("qrClass") as? QrString
//        brandValue.text = qrClass?.brand
//        theCountry.text = qrClass?.country
        nitrate_level_success.text = qrClass?.nitrate
        arsinic_level_success.text = qrClass?.arsinic
        copper_level_success.text = qrClass?.copper
        lead_level_success.text = qrClass?.lead
        mercury_level_success.text = qrClass?.mercury



        //Log.d("qrclassR",qrClass?.brand!!)

        val backButton = findViewById<Button>(R.id.backDetails)

        backButton.setOnClickListener{
            val intent = Intent(this, BatchDetails::class.java)
            startActivity(intent)
        }

    }
}