package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.newtest.Class.QrString
import com.google.firebase.firestore.FirebaseFirestore

class BatchDetails : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batchdetails)



        val batchId = intent.getStringExtra("batchId")
        val country = intent.getStringExtra("country")
        val brand = intent.getStringExtra("brand")





        val store = findViewById<Button>(R.id.confirm)

        store.setOnClickListener {

            val toxic_level_value = findViewById<EditText>(R.id.toxic_level)
            val collagen_level_value = findViewById<EditText>(R.id.collagen_level)
            val acidity_value = findViewById<EditText>(R.id.acidity)
            val authenticity_value = findViewById<TextView>(R.id.authenticity)
            val edible_saliva_value = findViewById<TextView>(R.id.edible_saliva)

            val toxiclevel = toxic_level_value.text.toString()
            val collagenlevel = collagen_level_value.text.toString()
            val acidity = acidity_value.text.toString()
            val authenticity = authenticity_value.text.toString()
            val ediblesaliva = edible_saliva_value.text.toString()

            //class will have batchid, collagen, acid, authen, saliva, country, date

            var intent = Intent(this,QrAdmin::class.java)
            var tQr: QrString = QrString(batchId,collagenlevel,acidity,authenticity,ediblesaliva,country,"",brand)

            intent.putExtra("tQr",tQr)
            intent.putExtra("qrClass",tQr)

            startActivity(intent)



    }







    }
}