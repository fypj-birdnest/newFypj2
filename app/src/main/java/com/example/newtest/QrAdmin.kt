package com.example.newtest

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newtest.Class.QrString
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class QrAdmin : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private val REQUEST_CAMERA = 1
    private var scannerViewA : ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerViewA = ZXingScannerView(this)
        setContentView(scannerViewA)

        if(!checkPermission())
            requestPermission()
    }

    private fun checkPermission() : Boolean{
        return ContextCompat.checkSelfPermission(this@QrAdmin,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),REQUEST_CAMERA)
    }

    override fun onResume(){
        super.onResume()
        scannerViewA?.setResultHandler(this)
        scannerViewA?.startCamera()

    }

    override fun onDestroy() {
        super.onDestroy()
        scannerViewA?.stopCamera()
        scannerViewA?.resumeCameraPreview (this)

    }

    override fun handleResult(p0: Result?) {
        val result = p0?.text
        val test = result


        val db = FirebaseFirestore.getInstance()
        db.collection("qr").get().addOnSuccessListener { result ->

            for (document in result) {
                Log.d("result of qr firebase",document.id)
                if (test == document.getString("code")) {

                    var qrClass = intent.getSerializableExtra("qrClass") as? QrString


                    val items = HashMap<String, Any>(

                    )
                    //var nitrate:String?,var arsinic:String?,var copper:String?,var lead:String?,var mercury:String?
                    items.put("nitrate", qrClass?.nitrate!!)
                    items.put("arsinic", qrClass.arsinic!!)
                    items.put("copper", qrClass.copper!!)
                    items.put("lead", qrClass.lead!!)
                    items.put("mercury", qrClass.mercury!!)
                    items.put("state", qrClass.state!!)
                    items.put("status", "active")





                    db.collection("qr").document(document.id).update(items).addOnSuccessListener {
                        var intent = Intent(this,QrFinished::class.java)
                        intent.putExtra("qrClass",qrClass)
                        startActivity(intent)
                    }.addOnFailureListener {
                        exception: java.lang.Exception ->  Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

        scannerViewA?.resumeCameraPreview (this)




    }
}