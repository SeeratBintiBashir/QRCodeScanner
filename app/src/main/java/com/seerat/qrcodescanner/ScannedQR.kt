package com.seerat.qrcodescanner

import java.sql.Timestamp

data class ScannedQR(val id: Int? = 0, val barcode: String? , val timestamp: String?, val remark1: String?, val remark2: String?)
