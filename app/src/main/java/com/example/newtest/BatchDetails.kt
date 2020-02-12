package com.example.newtest

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.newtest.Class.QrString
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_batchdetails.*
import kotlinx.android.synthetic.main.activity_batchprocessing.*
import java.util.*

class BatchDetails : AppCompatActivity(){

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

//    fun EBN_analytics(){
//        val intent = Intent(this, HomePage::class.java)
//        startActivity(intent)
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batchdetails)

        val store = findViewById<Button>(R.id.confirm)

        store.setOnClickListener {


            val nitrate_level_value = findViewById<EditText>(R.id.nitrate_level)
            val arsinic_level_value = findViewById<EditText>(R.id.arsinic_level)
            val copper_level_value = findViewById<EditText>(R.id.copper_level)
            val lead_level_value = findViewById<EditText>(R.id.lead_level)
            val mercury_level_value = findViewById<EditText>(R.id.mercury_level)


            val authenticity_value = findViewById<Spinner>(R.id.authenticity)



            val nitrateLevel = nitrate_level_value.text.toString()
            val arsinicLevel = arsinic_level_value.text.toString()
            val copperLevel = copper_level_value.text.toString()
            val leadLevel = lead_level_value.text.toString()
            val mercuryLevel = mercury_level_value.text.toString()

            val authenticity = authenticity_value.getSelectedItem().toString()
            if(nitrateLevel == "" || arsinicLevel == "" ||nitrateLevel == "" ||copperLevel == "" ||nitrateLevel == "" ||leadLevel == "" ||mercuryLevel == "" ){
                Toast.makeText(this,"Please fill up all fields", Toast.LENGTH_LONG).show()
            }else{
                //class will have batchid, collagen, acid, authen, saliva, country, date
                val batchId = intent.getStringExtra("batchId")
                val country = intent.getStringExtra("country")
                val brand = intent.getStringExtra("brand")
                var intent = Intent(this,QrAdmin::class.java)
                var tQr = QrString(batchId,nitrateLevel,arsinicLevel,copperLevel,leadLevel,mercuryLevel,country,brand,authenticity)
                intent.putExtra("qrClass",tQr) //Send all the data to QrAdmin
                startActivity(intent)
            }



        }

        val adapterAuthenticity = ArrayAdapter.createFromResource(this,
            R.array.authenticity_list,android.R.layout.simple_spinner_item)

        adapterAuthenticity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        authenticity.adapter = adapterAuthenticity
        
        //Alert Dialog
        val mAlertDialogBtn = findViewById<Button>(R.id.end_batch)

        mAlertDialogBtn.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this@BatchDetails)
            mAlertDialog.setMessage("Do you wish to end batch?")

            //Add positive button to alert dialog
            mAlertDialog.setPositiveButton("Yes"){dialog, id->
                val intent = Intent(this, BatchProcessing::class.java)
                startActivity(intent)
            }

            //Add negative button to alert dialog
            mAlertDialog.setNegativeButton("No"){dialog, id->
                dialog.dismiss()
            }

            mAlertDialog.show()
        }
    }
}