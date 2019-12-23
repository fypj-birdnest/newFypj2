package com.example.newtest

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class BatchDetails : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batchdetails)

        val db = FirebaseFirestore.getInstance()

        val store = findViewById<Button>(R.id.confirm)
        store.setOnClickListener {
            view: View? -> store()
        }
    }

    private fun store(){

        val db = FirebaseFirestore.getInstance()

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

        val items = hashMapOf(
                "toxicLevel" to toxiclevel,
                "collagenLevel" to collagenlevel,
                "acidity" to acidity,
                "authenticity" to authenticity,
                "edibleSaliva" to ediblesaliva

        )
        db.collection("batchDetails").document().set(items).addOnSuccessListener { void: Void? ->
            Toast.makeText(this, "Succeess", Toast.LENGTH_LONG).show()


        }
    }
}