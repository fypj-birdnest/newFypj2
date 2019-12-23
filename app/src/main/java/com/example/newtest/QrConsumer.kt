package com.example.newtest

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newtest.Class.QrString
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_results.*
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler
import java.io.Serializable

class QrConsumer : AppCompatActivity(),ResultHandler {
    private val REQUEST_CAMERA = 1
    private var scannerView :ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        scannerView?.resumeCameraPreview (this)

        if(!checkPermission())
            requestPermission()
    }

    private fun checkPermission() : Boolean{
        return ContextCompat.checkSelfPermission(this@QrConsumer,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),REQUEST_CAMERA)
    }

    override fun onResume(){
        super.onResume()

        if(checkPermission()){
            if(scannerView == null){
                scannerView = ZXingScannerView(this)
                setContentView(scannerView)
            }
            scannerView?.setResultHandler(this)
            scannerView?.startCamera()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()
        scannerView?.resumeCameraPreview (this)

    }

    override fun handleResult(p0: Result?) {
        var tmsg:String? = ""
        var check:Boolean? = false
        val tresult = p0?.text
        var test:String? = tresult


        var intent = Intent(this,Results::class.java)
        intent.putExtra("tQr",test)
        Log.d("runningQR",test)
        startActivity(intent)

//        val db = FirebaseFirestore.getInstance()
//
//        db.collection("qr").get().addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d("theResult", "${document.id} => ${document.data}")
//                if(test == document.getString("code")){
//                    check = true
//                    var tQr = QrString(document.getString("code"),document.getString("brand"),document.getString("authen"),document.getString("collagen"),document.getString("saliva"),document.getString("acidity"),document.getString("country"))
//                    var intent = Intent(this,Results::class.java)
//                    intent.putExtra("tQr", tQr as Serializable)
//                    startActivity(intent)
//                }
//            }
//        }.addOnFailureListener { exception ->
//            Log.w("GetDocError", "Error getting documents.", exception)
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Result")
//            builder.setMessage("The QR code is invalid")
//            builder.setPositiveButton("OK") {dialog, which ->
//                scannerView?.resumeCameraPreview(this@QrConsumer)
//                startActivity(intent)
//            }
//        }
        //scannerView?.resumeCameraPreview(this@QrConsumer)



//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Result")
//        builder.setPositiveButton("OK") {dialog, which ->
//            scannerView?.resumeCameraPreview(this@MainActivity)
//            startActivity(intent)
//        }



    }
}