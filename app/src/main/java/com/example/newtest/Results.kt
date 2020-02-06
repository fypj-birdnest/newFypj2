package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newtest.Class.QrString
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_results.*
import java.io.Serializable
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.bottom_nav.*
import net.glxn.qrgen.android.QRCode

class Results : AppCompatActivity(){

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
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)

    }

    fun EBN_analytics(){
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val db = FirebaseFirestore.getInstance()

        db.collection("tableValue").get().addOnSuccessListener { result ->

            for(document in result){

                if(document.id == "nitrate"){
                    tableNitrate.text = document.getString("value")

                }
                else if(document.id == "arsinic"){
                    tableArsinic.text = document.getString("value")

                }
                else if(document.id == "copper"){
                    tableCopper.text = document.getString("value")

                }
                else if(document.id == "lead"){
                    tableLead.text = document.getString("value")

                }
                else{
                    tableMercury.text = document.getString("value")

                }
            }
        }


        val tbitmap = QRCode.from("testing").withSize(500,500).bitmap()
        //(graph as ImageView).setImageBitmap(tbitmap)

        var theCheck = false
        db.collection("qr").get().addOnSuccessListener { result ->

            val qrcode = intent.getStringExtra("tQr")!!
            for (document in result) {
//                Log.d("another",qrcode)
//                Log.d("theResult", "${document.id} => ${document.data}")
                if(qrcode == document.getString("code") && document.getString("status") == "active"){
                    theCheck = true
                    brandValue.text = document.getString("brand")
                    countryOriginValue.text = document.getString("country")
                    collagenResult.text = document.getString("collagen")
                    resultNitrate.text = document.getString("nitrate")
                    resultArsinic.text = document.getString("arsinic")
                    resultCopper.text = document.getString("copper")
                    resultLead.text = document.getString("lead")
                    resultMercury.text = document.getString("mercury")
                    results.text = document.getString("state")
                }
            }
            if(!theCheck){
                results.text = "FAKE QR"
            }

        }.addOnFailureListener { exception ->
            Log.w("GetDocError", "Error getting documents.", exception)
        }



    }

//    private fun replaceFragment(fragment: Fragment){
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
//        fragmentTransaction.commit()
//    }

}

