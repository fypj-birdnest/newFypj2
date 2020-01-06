package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_analysis.*
import kotlinx.android.synthetic.main.activity_customization.*

class Customization : AppCompatActivity(){

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
        setContentView(R.layout.activity_customization)

//        val adapterX = ArrayAdapter.createFromResource(this,
//                R.array.x_list, android.R.layout.simple_spinner_item)
//
//        adapterX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        customization_spinner_x.adapter = adapterX

        //spinner for data spinner
        val adapterData = ArrayAdapter.createFromResource(this,
            R.array.data_list, android.R.layout.simple_spinner_item)

        adapterData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        data_spinner.adapter = adapterData


        //spinner for y axis
        val adapterY = ArrayAdapter.createFromResource(this,
                R.array.y_list, android.R.layout.simple_spinner_item)

        adapterY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        customization_spinner_y.adapter = adapterY

        //spinner for month start
        val adapterMonth = ArrayAdapter.createFromResource(this,
            R.array.month_list, android.R.layout.simple_spinner_item)

        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        month_spinner_start.adapter = adapterMonth
        month_spinner_end.adapter = adapterMonth

        //spinner for year start
        val adapterYear = ArrayAdapter.createFromResource(this,
            R.array.year_list, android.R.layout.simple_spinner_item)

        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        year_spinner_start.adapter = adapterYear
        year_spinner_end.adapter = adapterYear


        val theButton = findViewById<Button>(R.id.viewAnalysis)
        theButton.setOnClickListener{
            customization_graph.setImageResource(R.drawable.loading)
            val sdate_field = findViewById<Spinner>(R.id.month_spinner_start)
            val edate_field = findViewById<Spinner>(R.id.month_spinner_end)
            val syear_field = findViewById<Spinner>(R.id.year_spinner_start)
            val eyear_field = findViewById<Spinner>(R.id.year_spinner_end)
            val theY_field = findViewById<Spinner>(R.id.customization_spinner_y)
            val data_field = findViewById<Spinner>(R.id.data_spinner)

            var smonth:String
            var emonth:String
            var tdata:String
            var theY:String
            if(sdate_field.selectedItemPosition < 9){
                smonth = "0" + (sdate_field.selectedItemPosition + 1).toString()
            }else{
                smonth = (sdate_field.selectedItemPosition + 1).toString()
            }

            if(edate_field.selectedItemPosition < 9){
                emonth = "0" + (edate_field.selectedItemPosition + 1).toString()
            }else{
                emonth = (edate_field.selectedItemPosition + 1).toString()
            }



            var syear = syear_field.selectedItem.toString()
            var eyear = eyear_field.selectedItem.toString()
            var sdate = syear + smonth
            var edate = eyear + emonth

            if(theY_field.selectedItem.toString() == "Acidity"){
                theY = "acidity"
            }else if(theY_field.selectedItem.toString()=="Collagen Level"){
                theY =  "collagen"
            }else{
                theY = "saliva"
            }

            if(data_field.selectedItem.toString() == "Brand"){
                tdata = "brand"
            }else{
                tdata = "country"
            }


//            var tToast = Toast.makeText(this,sdate+edate+theY+tdata,Toast.LENGTH_LONG)
//            tToast.show()

            val firefunc = FirebaseFunctions.getInstance()
            val db = FirebaseFirestore.getInstance()

            val data = hashMapOf(
                    "startDate"  to sdate,
                    "endDate"  to edate,
                    "theY"  to theY,
                    "tLine"  to tdata
            )

            var customG = "customGraph" + theY + tdata

            firefunc.getHttpsCallable("customGraph").call(data)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful)
                        {
                            db.collection("graphImages").get().addOnSuccessListener { result ->
                                for (document in result) {
                                    if(customG == document.id){
                                        Picasso.get().invalidate(document.getString("url"))

                                        Picasso
                                                .get()
                                                .load(document.getString("url"))
                                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                                .placeholder(R.drawable.placeholder)
                                                .into(customization_graph)
                                    }

                                }
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


    }

//    fun showHide(layout_date:LinearLayout){  //If date option is clicked, show the linear layout
//        layout_date.visibility = if (layout_date.visibility == LinearLayout.VISIBLE)
//        {
//            LinearLayout.INVISIBLE
//        }
//
//        else
//        {
//            LinearLayout.VISIBLE
//        }
//    }

