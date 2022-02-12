package com.nghiemtuananh.readrsskpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        var link: String = intent.getStringExtra("linkTinTuc") as String
//        Toast.makeText(this, link, Toast.LENGTH_LONG).show()
        wv_tintuc.loadUrl(link)
        wv_tintuc.webViewClient = WebViewClient()
    }
}