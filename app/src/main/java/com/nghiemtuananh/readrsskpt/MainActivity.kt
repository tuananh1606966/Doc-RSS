package com.nghiemtuananh.readrsskpt

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    var arrayTitle: ArrayList<String> = arrayListOf()
    var arrayLink: ArrayList<String> = arrayListOf()
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTitle)
        lv_tieude.adapter = adapter
        ReadRSS().execute("https://vnexpress.net/rss/so-hoa.rss")
        lv_tieude.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(this, arrayLink.get(position), Toast.LENGTH_LONG).show()
            intent = Intent(this, NewActivity::class.java)
            intent.putExtra("linkTinTuc", arrayLink.get(position))
            startActivity(intent)
        }
    }

    inner class ReadRSS : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            var content = StringBuilder()
            val url = URL(params[0])
            val inputStreamReader = InputStreamReader(url.openConnection().getInputStream())
            val bufferedReader = BufferedReader(inputStreamReader)
            var line = bufferedReader.readLine()
            while (line != null) {
                content.append(line)
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
            return content.toString()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            var parser = XMLDOMParser()
            var document = parser.getDocument(result)
            var nodeList: NodeList = document!!.getElementsByTagName("item")
            var tieuDe = ""
            for (i in 0..nodeList.length - 1) {
                var element: Element = nodeList.item(i) as Element
                tieuDe = parser.getValue(element, "title")
                arrayTitle.add(tieuDe)
                arrayLink.add(parser.getValue(element, "link"))
            }
            adapter.notifyDataSetChanged()
//                Toast.makeText(this@MainActivity, tieuDe, Toast.LENGTH_LONG)
//                    .show()
        }
    }
}