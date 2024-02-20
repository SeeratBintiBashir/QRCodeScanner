package com.seerat.qrcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.seerat.qrcodescanner.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var db: DatabaseHelper
    private lateinit var historyAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setBottombar()


        db = DatabaseHelper(this)
        historyAdapter = Adapter(db.getAllScannedQR(),this)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = historyAdapter

        binding.bottombar.imageIcon.setImageDrawable(getDrawable(R.drawable.icon_barcode))



    }



    fun setToolbar(){
        binding.toolbar.toolbartitle.visibility = View.GONE
        binding.toolbar.iconBack.setOnClickListener {
            onBackPressed()
    }

}

    fun setBottombar(){
        binding.bottombar.iconHistory.visibility = View.GONE
//        binding.bottombar.imageIcon.visibility = View.GONE

        binding.bottombar.iconHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}