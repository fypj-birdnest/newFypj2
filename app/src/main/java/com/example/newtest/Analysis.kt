package com.example.newtest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_analysis.*
import kotlinx.android.synthetic.main.activity_results.*

class Analysis : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        val adapter = ArrayAdapter.createFromResource(this,
                R.array.details_list, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        val db = FirebaseFirestore.getInstance()
        val firefunc = FirebaseFunctions.getInstance()

        val tQr = intent.getStringExtra("tQr")
        val data = hashMapOf(
            "text"  to tQr,
            "push" to true
        )
        spinner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                grapha.setImageResource(R.drawable.loading)
                if(position == 0){


                    firefunc.getHttpsCallable("brandQuality5").call(data)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful)
                            {
                                db.collection("graphImages").get().addOnSuccessListener { result ->
                                    for (document in result) {
                                        //Log.d("theResult", "${document.id} => ${document.data}")
                                        if("brandQuality5" == document.id){
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

                                val e = task.exception
                                if (e is FirebaseFunctionsException)
                                {
                                    Log.d("error in calling func", "Result3: " + e.code + e.details)
                                }
                            }
                            return@addOnCompleteListener
                        }
                }else if(position==1){

                    firefunc.getHttpsCallable("brandCollagen5").call(data)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful)
                            {
                                db.collection("graphImages").get().addOnSuccessListener { result ->
                                    for (document in result) {
                                        //Log.d("theResult", "${document.id} => ${document.data}")
                                        if("brandCollagen5" == document.id){
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

                                val e = task.exception
                                if (e is FirebaseFunctionsException)
                                {
                                    Log.d("error in calling func", "Result3: " + e.code + e.details)
                                }
                            }
                            return@addOnCompleteListener
                        }

                }else if(position==2){

                    firefunc.getHttpsCallable("brandAcid5").call(data)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful)
                            {
                                db.collection("graphImages").get().addOnSuccessListener { result ->
                                    for (document in result) {
                                        //Log.d("theResult", "${document.id} => ${document.data}")
                                        if("brandAcid5" == document.id){
                                            Picasso.get().invalidate(document.getString("url"))
                                            Picasso
                                                .get()
                                                .load(document.getString("url"))
                                                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE )
                                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                                .placeholder(R.drawable.placeholder)
                                                .into(grapha)
                                            Log.d("link for acid",document.getString("url")!!)
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

                }else{


                    firefunc.getHttpsCallable("brandQuality5").call(data)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful)
                            {
                                db.collection("graphImages").get().addOnSuccessListener { result ->
                                    for (document in result) {
                                        //Log.d("theResult", "${document.id} => ${document.data}")
                                        if("brandQuality5" == document.id){
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



            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }


    }

}