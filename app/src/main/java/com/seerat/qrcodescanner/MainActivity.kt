package com.seerat.qrcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.Result
import com.seerat.qrcodescanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHelper
    lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.iconBack.visibility = View.GONE
        codeScanner = CodeScanner(this, binding.scanArea)


        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        codeScanner.setDecodeCallback { result ->
         handelScannedBarcode(result.toString())
        }


        codeScanner.setErrorCallback { result ->
//            Log.e("123", "rawResult" + result.toString())
//            Toast.makeText(this.applicationContext, result.toString(), Toast.LENGTH_LONG).show()
        }
        codeScanner.startPreview()

        db = DatabaseHelper(this)
      //  db.open()

        binding.bottombar.iconHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
    fun handelScannedBarcode(barcode:String){
        db.addScannedQR(ScannedQR(0,barcode,"","",""))

        openResultBarcode(barcode)

        Log.e("123", "rawResult" + barcode.toString())
        this.runOnUiThread {
            Toast.makeText(MainActivity@this, barcode, Toast.LENGTH_LONG).show()
        }



    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    fun openResultBarcode(barcode: String){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("key", barcode)
        startActivity(intent)
    }




}