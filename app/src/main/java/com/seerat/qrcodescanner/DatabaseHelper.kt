package com.seerat.qrcodescanner

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.ContactsContract


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "qrscanerapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "scan_history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_BARCODE = "barcode"
        private const val COLUMN_SCAN_TIMESTAMP = "scan_timestamp"
        private const val COLUMN_REMARK_1 = "remark1"
        private const val COLUMN_REMARK_2 = "remark2"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_BARCODE TEXT NOT NULL," +
                "$COLUMN_SCAN_TIMESTAMP TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "$COLUMN_REMARK_1 TEXT," +
                "$COLUMN_REMARK_2 TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun addScannedQR(scannedQr: ScannedQR){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BARCODE, scannedQr.barcode)
            put(COLUMN_REMARK_1, scannedQr.remark1)
            put(COLUMN_REMARK_2, scannedQr.remark2)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }


    fun getAllScannedQR(): List<ScannedQR> {
        val scannedQRList = mutableListOf<ScannedQR>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val barcode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BARCODE))
            val timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCAN_TIMESTAMP))
            val remark1 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMARK_1))
            val remark2 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMARK_2))

            val scannedQr = ScannedQR(id,barcode,timestamp,remark1,remark2)
            scannedQRList.add(scannedQr)
        }
        cursor.close()
        db.close()
        return scannedQRList
    }


    fun getScannedQRById(Id: Int): ScannedQR{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $Id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val barcode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BARCODE))
        val timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SCAN_TIMESTAMP))
        val remark1 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMARK_1))
        val remark2 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMARK_2))

        cursor.close()
        db.close()
        return ScannedQR(id, barcode,timestamp,remark1,remark2)
    }

    fun removeScannedQR(Id: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(Id.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun removeAllScannedQR(Id: Int){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        db.delete(TABLE_NAME, query, null)
        db.close()
    }

}