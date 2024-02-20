package com.seerat.qrcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.seerat.qrcodescanner.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var result: String
    private var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setToolbar()
        setBottombar()

        result = intent.getStringExtra("key").toString()
        binding.tvScanResult.text = result



    }

    fun setToolbar(){
        binding.toolbar.toolbartitle.text = "Scan Result"
        binding.toolbar.iconBack.setOnClickListener {
            onBackPressed()
        }

    }

    fun setBottombar(){
        binding.bottombar.iconHistory.visibility = View.GONE
        binding.bottombar.shareIcon.visibility = View.VISIBLE
        binding.bottombar.iconBrightness.visibility = View.GONE
        binding.bottombar.iconSearch.visibility = View.VISIBLE
        binding.bottombar.imageIcon.visibility = View.GONE
//        binding.bottombar.copyIcon.visibility = View.VISIBLE

        binding.bottombar.iconHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}