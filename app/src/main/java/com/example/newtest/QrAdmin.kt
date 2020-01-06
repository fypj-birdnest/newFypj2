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

                   // var batchId : Any = qrClass?.batchId!!

//                    val tQr = hashMapOf(
//                            "batchId" to qrClass.batchId,
//                            "acidity" to qrClass.acid,
//                            "brand" to qrClass.brand,
//                            "collagen" to qrClass.collagen,
//                            "country" to qrClass.country,
//                            "date" to qrClass.tdate,
//                            "saliva" to qrClass.saliva,
//                            "state" to qrClass.state,
//                            "status" to "active"
//                    )

                    val items = HashMap<String, Any>(

                    )

                    items.put("acidity", qrClass?.acid!!)
                    items.put("collagen", qrClass.collagen!!)
                    items.put("date", qrClass.tdate!!)
                    items.put("saliva", qrClass.saliva!!)
                    items.put("state", qrClass.state!!)
                    items.put("status", "active")





                    db.collection("qr").document(document.id).update(items).addOnSuccessListener {
                        var intent = Intent(this,BatchDetails::class.java)

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