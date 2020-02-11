package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_adminscanresults.*

class AdminScanResults : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminscanresults)

        val db = FirebaseFirestore.getInstance()
        db.collection("tableValue").get().addOnSuccessListener { result ->
            for (document in result) {
//                Log.d("another",qrcode)
//                Log.d("theResult", "${document.id} => ${document.data}")
                if (document.id == "nitrate") {
                    tableNitrate2.setText(document.getString("value"))
                }else if(document.id == "arsinic"){
                    tableArsinic.setText(document.getString("value"))
                }else if(document.id == "copper") {
                    tableCopper.setText(document.getString("value"))
                }else if(document.id == "lead") {
                    tableLead.setText(document.getString("value"))
                }else{
                    tableMercury.setText(document.getString("value"))
                }

            }
        }
        val cfm = findViewById<Button>(R.id.confirm_scan_results)

        cfm.setOnClickListener {
            val hashNitrate = HashMap<String, Any>(

            )
            val hashArsinic = HashMap<String, Any>(

            )
            val hashCopper = HashMap<String, Any>(

            )
            val hashLead = HashMap<String, Any>(

            )
            val hashMercury = HashMap<String, Any>(

            )


            hashNitrate.put("value", tableNitrate2.text.toString())
            hashArsinic.put("value", tableArsinic.text.toString())
            hashCopper.put("value", tableCopper.text.toString())
            hashLead.put("value", tableLead.text.toString())
            hashMercury.put("value", tableMercury.text.toString())

            db.collection("tableValue").document("nitrate").update(hashNitrate)
            db.collection("tableValue").document("arsinic").update(hashArsinic)
            db.collection("tableValue").document("copper").update(hashCopper)
            db.collection("tableValue").document("lead").update(hashLead)
            db.collection("tableValue").document("mercury").update(hashMercury)



            var intent = Intent(this,AdminHomePage::class.java)
            Toast.makeText(this,"The Table has been edited", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

    }
}