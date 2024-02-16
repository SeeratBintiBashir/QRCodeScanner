package com.seerat.qrcodescanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.Result
import com.seerat.qrcodescanner.databinding.ActivityMainBinding
import me.dm7.barcodescanner.zxing.ZXingScannerView

class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.iconBack.visibility = View.GONE

        binding.scanArea.startCamera()

    }

    override fun handleResult(rawResult: Result?) {
        Toast.makeText(this.applicationContext,rawResult.toString(),Toast.LENGTH_LONG).show()
    }
}