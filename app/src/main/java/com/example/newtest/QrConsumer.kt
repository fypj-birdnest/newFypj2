package com.example.newtest

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        //scannerView?.resumeCameraPreview (this)

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
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()

    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView?.stopCamera()
        scannerView?.resumeCameraPreview (this)

    }

    override fun handleResult(p0: Result?) {
        val result = p0?.text
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("check tqr")
//        builder.setMessage(result)
//        builder.show()

        var intent = Intent(this,Results::class.java)
        intent.putExtra("tQr",result)
        if(result.isNullOrEmpty()){
            Log.d("empty","the test is empty")
        }else{
            startActivity(intent)
        }
        scannerView?.resumeCameraPreview (this)




    }
}