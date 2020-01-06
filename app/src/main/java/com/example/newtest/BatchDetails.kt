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
        setContentView(R.layout.activity_batchdetails)

        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        var ddate:String? = null

        //DatePickerDialog
        date_details.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                //set to TextView
                var displayDate = "" + mDay + "/" + (mMonth+1) + "/" + mYear
                ddate = "" + mYear + (mMonth+1) + mDay
                date_details.setText(displayDate)
            }, year, month, day )
            //show dialog
            dpd.show()
        }


        val store = findViewById<Button>(R.id.confirm)

        store.setOnClickListener {


            val collagen_level_value = findViewById<EditText>(R.id.collagen_level)
            val acidity_value = findViewById<EditText>(R.id.acidity)
            val authenticity_value = findViewById<Spinner>(R.id.authenticity)
            val edible_saliva_value = findViewById<TextView>(R.id.edible_saliva)

            val dateM = ddate
            val collagenlevel = collagen_level_value.text.toString()
            val acidity = acidity_value.text.toString()
            val authenticity = authenticity_value.getSelectedItem().toString()
            val ediblesaliva = edible_saliva_value.text.toString()

            //class will have batchid, collagen, acid, authen, saliva, country, date
            val batchId = intent.getStringExtra("batchId")
            val country = intent.getStringExtra("country")
            val brand = intent.getStringExtra("brand")
            var intent = Intent(this,QrAdmin::class.java)
            var tQr: QrString = QrString(batchId,collagenlevel,acidity,authenticity,ediblesaliva,country,dateM,brand, dateM)
            intent.putExtra("qrClass",tQr)
            startActivity(intent)

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