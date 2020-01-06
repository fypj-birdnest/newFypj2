package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_analysis.*
import kotlinx.android.synthetic.main.activity_results.*
import kotlinx.android.synthetic.main.bottom_nav.*

class Analysis : AppCompatActivity(){

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
        setContentView(R.layout.activity_analysis)

        val adapter = ArrayAdapter.createFromResource(this,
                R.array.details_list, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter



        var check = hashMapOf<String,Boolean>(

        )
        check.put("quality",false)
        check.put("collagen",false)
        check.put("acid",false)

        execPythonFunc("brandQuality5")
        check["quality"] = true

        spinner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                grapha.setImageResource(R.drawable.loading)
                var theString = "quality"
                if(position == 0){
                    if(check["quality"]!!){
                        displayGraph("brandQuality5")
                    }else{
                        execPythonFunc("brandQuality5")
                        check["quality"] = true
                    }
                }else if(position == 1){
                    if(check["collagen"]!!){
                        displayGraph("brandCollagen5")
                    }else{
                        execPythonFunc("brandCollagen5")
                        check["collagen"] = true
                    }
                }else if(position == 2){
                    if(check["acid"]!!){
                        displayGraph("brandAcid5")
                    }else{
                        execPythonFunc("brandAcid5")
                        check["acid"] = true
                    }
                }
            }



            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

    }


    fun displayGraph(theString: String){
        val db = FirebaseFirestore.getInstance()
        db.collection("graphImages").get().addOnSuccessListener { result ->
            for (document in result) {
                //Log.d("theResult", "${document.id} => ${document.data}")
                if(theString == document.id){
                    Log.d("theResult",document.getString("url")!! )
                    Picasso.get().invalidate(document.getString("url"))
                    Picasso
                            .get()
                            .load(document.getString("url"))
                            .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE )
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .placeholder(R.drawable.placeholder)
                            .into(grapha)

                }
            }
        }.addOnFailureListener { exception ->
            Log.w("GetDocError", "Error getting documents.", exception)
        }
    }
    fun execPythonFunc(theString: String){

        val firefunc = FirebaseFunctions.getInstance()
        val tQr = intent.getStringExtra("tQr")
        val data = hashMapOf(
                "text"  to tQr,
                "push" to true
        )

        firefunc.getHttpsCallable(theString).call(data)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful)
                    {
                        displayGraph(theString)

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