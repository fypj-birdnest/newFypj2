package com.example.newtest

import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_batchprocessing.*
import java.util.*
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*

class BatchProcessing : AppCompatActivity(){

    var sdate:String? = null

//    val manager = supportFragmentManager
//
//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item->
//        when(item.itemId){
//            R.id.authenticate_EBN ->{
//                createFragmentOne()
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.EBN_analytics->{
//                createFragmentTwo()
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

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
        setContentView(R.layout.activity_batchprocessing)

        //Start button
        //Needs update because of batch ID
        //Skip button
        skip.setOnClickListener {
            val intent = Intent(this, BatchDetails::class.java)
            startActivity(intent)
        }
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var code = ""
        for (i in 0..10) {
            code += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        val batch_id = findViewById<TextView>(R.id.batch_id_processing_value)
        batch_id.text = code

//        createFragmentOne()
//        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)



        val store = findViewById<Button>(R.id.start)
        store.setOnClickListener {
            view: View? -> store()
        }
        //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //DatePickerDialog
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
        val adapterCountry = ArrayAdapter.createFromResource(this,
            R.array.countries_list,android.R.layout.simple_spinner_item)

        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        country_spinner.adapter = adapterCountry

//        val locales = Locale.getAvailableLocales()
//        val countries = ArrayList<String>()
//        for (locale in locales) {
//            val country = locale.displayCountry
//            if (country.trim { it <= ' ' }.length > 0 && !countries.contains(country)) {
//                countries.add(country)
//            }
//        }
//        countries.add(0, "Please select country")
//
//        Collections.sort(countries)
//        for (country in countries) {
//            println(country)
//        }
//
//        val countryAdapter = ArrayAdapter(this,
//                android.R.layout.simple_spinner_item, countries)
//
//        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        // Apply the adapter to the your spinner
//        country_spinner.setAdapter(countryAdapter)

        skip.setOnClickListener {
            val intent = Intent(this, BatchDetails::class.java)
            startActivity(intent)
        }

    }

//    fun randomAlphaNumericString(desiredStrLength: Int): String { //Randomize
//        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
//
//        return (1..desiredStrLength)
//            .map{ kotlin.random.Random.nextInt(0, charPool.size) }
//            .map(charPool::get)
//            .joinToString("")
//    }

    private fun store(){
        val db = FirebaseFirestore.getInstance()
        val batch_id = findViewById<TextView>(R.id.batch_id_processing_value)
        val brand_name_value = findViewById<EditText>(R.id.brand_name)
        val no_of_product_value = findViewById<TextView>(R.id.no_of_products)
        val country_of_origin_value = findViewById<Spinner>(R.id.country_spinner)
        val dateTv_value = findViewById<TextView>(R.id.dateTv)
        //val prodNo_value = findViewById<EditText>(R.id.productNo)


        val batchid = batch_id.text.toString()
        val brandname = brand_name_value.text.toString()
        val noofproduct = no_of_product_value.text.toString()
        val country = country_of_origin_value.getSelectedItem().toString()
        val dateM = dateTv_value.text
        //var prodNo = prodNo_value.text.toString()
        var newProd = noofproduct.toInt()


        val checkpickDateTxt = findViewById<EditText>(R.id.pickDateTxt)
        val pickDate = checkpickDateTxt.text.toString()

        if(!brandname.isEmpty() && !pickDate.isEmpty()){

                val items = hashMapOf( //rmb to include user
                        "batchId" to batchid,
                        "brand" to brandname,
                        "country" to country,
                        "date" to dateM, //this is the scanned date for the batch
                        "productNo" to noofproduct

                )
                db.collection("batchProcessing").document().set(items).addOnSuccessListener{
                    void: Void? -> Toast.makeText(this, "Succeess", Toast.LENGTH_LONG).show()


                    for(i in 1..newProd){
                        //generate the qr code
                        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                        var code = ""
                        for (i in 0..10) {
                            code += chars[Math.floor(Math.random() * chars.length).toInt()]
                        }
                        var nqr = hashMapOf(
                                "code" to code,
                                "status" to "inactive",
                                "batchId" to batchid,
                                "brand" to brandname,
                                "country" to country


                        )
                        db.collection("qr").document().set(nqr).addOnSuccessListener{
                            db.collection("newQr").document().set(nqr)
                        }.addOnFailureListener {
                            exception: java.lang.Exception ->  Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }


                    var intent = Intent(this,BatchDetails::class.java)
                    intent.putExtra("batchId",batchid)
                    intent.putExtra("country",country)
                    intent.putExtra("brand",brandname)
                    startActivity(intent)
                }.addOnFailureListener {
                    exception: java.lang.Exception ->  Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }

        }
        else{

            Toast.makeText(this,"Please fill up the fields",Toast.LENGTH_LONG).show()
        }
    }



//    fun createFragmentOne(){
//        val transaction = manager.beginTransaction()
//        val fragment = AuthenticateEBNFragment()
//        transaction.replace(R.id.fragmentContainer,fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
//
//    fun createFragmentTwo(){
//        val transaction = manager.beginTransaction()
//        val fragment = ViewEBNFragment()
//        transaction.replace(R.id.fragmentContainer,fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
}