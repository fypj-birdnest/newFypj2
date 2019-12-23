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
import android.widget.TextView
import com.google.gson.Gson


class Results : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val db = FirebaseFirestore.getInstance()
        val firefunc = FirebaseFunctions.getInstance()

//        val tQr = intent.extras?.get("tQr") as QrString
//
//        brandValue.text = tQr.brand
//        countryOriginValue.text = tQr.country
//        acidityLevelValue.text = tQr.acidity



        db.collection("qr").get().addOnSuccessListener { result ->

            val qrcode = intent.getStringExtra("tQr")!!
            for (document in result) {
                Log.d("another",qrcode)
                Log.d("theResult", "${document.id} => ${document.data}")
                if(qrcode == document.getString("code")){
                    brandValue.text = document.getString("brand")
                    countryOriginValue.text = document.getString("country")
                    acidityLevelValue.text = document.getString("acidity")
                    //insert graph code here

                    val data = hashMapOf(
                        "text"  to document.getString("brand"),
                        "push" to true
                    )

                    firefunc.getHttpsCallable("brandQuality5").call(data)
                        .addOnCompleteListener { task ->

                            if (!task.isSuccessful)
                            {
                                db.collection("graphImages").get().addOnSuccessListener { result ->
                                    for (document in result) {
                                        //Log.d("theResult", "${document.id} => ${document.data}")
                                        if("brandQuality5" == document.id){
                                            Picasso
                                                .get()
                                                .load(document.getString("url"))
                                                .into(graph)
                                        }
                                    }
                                }.addOnFailureListener { exception ->
                                    Log.w("GetDocError", "Error getting documents.", exception)
                                }

                                val e = task.exception
                                if (e is FirebaseFunctionsException)
                                {
                                    Log.d("error in calling func", "Result3: " + e.code + e.details)
                                }
                            }
                            return@addOnCompleteListener
                        }


                }
            }

        }.addOnFailureListener { exception ->
            Log.w("GetDocError", "Error getting documents.", exception)
        }







        val clickHere = findViewById<TextView>(R.id.viewAnalysis)
        clickHere.setOnClickListener {
            var intent = Intent(this,Analysis::class.java)
            intent.putExtra("tQr",brandValue.text)
            startActivity(intent)
        }


    }
}

