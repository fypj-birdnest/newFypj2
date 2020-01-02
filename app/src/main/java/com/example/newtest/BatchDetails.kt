package com.example.newtest

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_batchdetails.*
import kotlinx.android.synthetic.main.activity_batchprocessing.*

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

        val db = FirebaseFirestore.getInstance()

        val store = findViewById<Button>(R.id.confirm)
        store.setOnClickListener {
            view: View? -> store()
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

    private fun store(){

        val db = FirebaseFirestore.getInstance()

        val toxic_level_value = findViewById<EditText>(R.id.toxic_level)
        val collagen_level_value = findViewById<EditText>(R.id.collagen_level)
        val acidity_value = findViewById<EditText>(R.id.acidity)
        val authenticity_value = findViewById<Spinner>(R.id.authenticity)
        val edible_saliva_value = findViewById<TextView>(R.id.edible_saliva)

        val toxiclevel = toxic_level_value.text.toString()
        val collagenlevel = collagen_level_value.text.toString()
        val acidity = acidity_value.text.toString()
        val authenticity = authenticity_value.getSelectedItem().toString()
        val ediblesaliva = edible_saliva_value.text.toString()

        val items = hashMapOf(
                "toxicLevel(%)" to toxiclevel,
                "collagenLevel(%)" to collagenlevel,
                "acidity(%)" to acidity,
                "authenticity" to authenticity,
                "edibleSaliva(%)" to ediblesaliva

        )
        db.collection("batchDetails").document().set(items).addOnSuccessListener { void: Void? ->
            Toast.makeText(this, "Succeess", Toast.LENGTH_LONG).show()
        }
    }
}