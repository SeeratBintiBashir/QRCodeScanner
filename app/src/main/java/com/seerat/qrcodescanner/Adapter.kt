package com.seerat.qrcodescanner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private var results: List<ScannedQR>, context: Context):
    RecyclerView.Adapter<Adapter.HistoryViewHolder>() {


    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val qrResult: TextView = itemView.findViewById(R.id.qrResult)
        val timestamp: TextView = itemView.findViewById(R.id.timeStamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qr_history, parent, false)
        return HistoryViewHolder(view)    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = results[position]
        holder.qrResult.text = history.barcode
        holder.timestamp.text = history.timestamp


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ResultActivity::class.java).apply {
                putExtra("key", history.barcode)

            }
            holder.itemView.context.startActivity(intent)

        }

    }
}