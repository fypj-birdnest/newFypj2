package com.example.newtest

import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_batchprocessing.*
import java.util.*
import kotlin.collections.HashMap
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*
import java.lang.Exception


class BatchProcessing : AppCompatActivity(){

    var sdate:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batchprocessing)

        val db = FirebaseFirestore.getInstance()

        val store = findViewById<Button>(R.id.start)
        store.setOnClickListener {
            view: View? -> store()
        }
        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //button click to show DatePickerDialog
        pickDateTxt.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                //set to TextView
                var display_date = "" + mDay + "/" + (mMonth+1) + "/" + mYear
                sdate = "" + mYear + (mMonth+1) + mDay
                pickDateTxt.setText(display_date)
            }, year, month, day )
            //show dialog
            dpd.show()
        }



        //Spinner for the countries
        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.length > 0 && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.add(0, "Please select country")

        Collections.sort(countries)
        for (country in countries) {
            println(country)
        }

        val countryAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, countries)

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the your spinner
        country_spinner.setAdapter(countryAdapter)
    }

    private fun store(){
        val db = FirebaseFirestore.getInstance()
        val batch_id_value = findViewById<EditText>(R.id.batch_id)
        val brand_name_value = findViewById<EditText>(R.id.brand_name)
        val country_of_origin_value = findViewById<Spinner>(R.id.country_spinner)
        val dateTv_value = findViewById<TextView>(R.id.dateTv)

        val batch = batch_id_value.text.toString()
        val brandname = brand_name_value.text.toString()
        val country = country_of_origin_value.getSelectedItem().toString()
        val dateM = sdate

        Log.d("woop",brandname)

        val checkpickDateTxt = findViewById<EditText>(R.id.pickDateTxt)
        val pickDate = checkpickDateTxt.text.toString()

        if(!batch.isEmpty() && !brandname.isEmpty()  && !pickDate.isEmpty()){
            try{
                val items = hashMapOf(
                        "batchId" to batch, //does it exist in db
                        "brandname" to brandname,
                        "countryofOrigin" to country,
                        "date" to dateM

                )
                db.collection("batchProcessing").document(batch).set(items).addOnSuccessListener{
                    void: Void? -> Toast.makeText(this, "Succeess", Toast.LENGTH_LONG).show()
                }.addOnFailureListener {
                    exception: java.lang.Exception ->  Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }

            catch(e:Exception){

                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
            }
        }
        else{

            Toast.makeText(this,"Please fill up the fields",Toast.LENGTH_LONG).show()
        }
    }
}