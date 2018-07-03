package com.lucasmarciano.anko

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.lucasmarciano.anko.utils.NetworkHelper
import kotlinx.android.synthetic.main.activity_second.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class SecondActivity : AppCompatActivity() {
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val networkListener = NetworkHelper.newInstance()

        val valueId = intent.getIntExtra("id", 0)
        val valueSecond = intent.getIntExtra("second", 0)

        toast("id -> $valueId")
        toast("second -> $valueSecond")

        doAsync {
            if (networkListener.isWifiConnected(context)) // Check if are internet connection with the wifi
                icone_wifi.background = ContextCompat.getDrawable(context, R.drawable.ic_network_on)
            else
                icone_wifi.background = ContextCompat.getDrawable(context, R.drawable.ic_network_off)

            if (networkListener.isMobileDataConnected(context)) // Check if are internet connection with the cellphone data
                icone_tres_g.background = ContextCompat.getDrawable(context, R.drawable.ic_network_cell_on)
            else
                icone_tres_g.background = ContextCompat.getDrawable(context, R.drawable.ic_network_cell_off)


            if (NetworkHelper.isInternetConnected(context)) // Check if are internet connection with any tipe of connection of internet
                box.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            else
                box.setBackgroundColor(ContextCompat.getColor(context, R.color.red))

        }

    }
}
