package com.example.newtest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val db = FirebaseFirestore.getInstance()

        val clickHere = findViewById<Button>(R.id.loginButton)
        clickHere.setOnClickListener {
            var username = tusername.text.toString()
            var password = tpassword.text.toString()
            var check = false
            db.collection("users").get().addOnSuccessListener { result ->
                for (document in result) {
//                Log.d("another",qrcode)
//                Log.d("theResult", "${document.id} => ${document.data}")
                    if (username == document.getString("username")) {
                        check = true
                        if (password == document.getString("password")) {
                            var intent = Intent(this,BatchProcessing::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this,"Incorrect password", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                if (!check) {
                    Toast.makeText(this,"Username does not exist", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}