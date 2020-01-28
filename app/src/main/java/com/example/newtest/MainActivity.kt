package com.example.newtest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler



class MainActivity : AppCompatActivity(),ResultHandler {
    private val REQUEST_CAMERA = 1
    private var scannerView :ZXingScannerView? = null


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
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)

    }

    fun EBN_analytics(){
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        scannerView?.resumeCameraPreview (this)


        if(!checkPermission())
            requestPermission()

    }


    private fun checkPermission() : Boolean{
        return ContextCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
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
        val result = p0?.text
        var test:String? = result

        val db = FirebaseFirestore.getInstance()

        db.collection("qr").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d("theResult", "${document.id} => ${document.data}")
                if(test == document.getString("code")){
                    check = true
                }
            }
        }.addOnFailureListener { exception ->
            Log.w("GetDocError", "Error getting documents.", exception)
        }

//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Result")
//        builder.setPositiveButton("OK") {dialog, which ->
//            scannerView?.resumeCameraPreview(this@MainActivity)
//            startActivity(intent)
//        }


            fun trueQr(){
//                tmsg = "The QR Code exist in database"
//                builder.setMessage(tmsg)
//                val alert = builder.create()
//                alert.show()
                //intent to go to next page
                val intent = Intent(this@MainActivity, Results::class.java)
                startActivity(intent)
            }

            fun falseQr() {
//                tmsg = "The QR Code is fake"
//                builder.setMessage(tmsg)
//                val alert = builder.create()
//                alert.show()
            }

    }
}
