package com.lucasmarciano.anko

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lucasmarciano.anko.utils.NetworkHelper
import kotlinx.android.synthetic.main.activity_second.*
import org.jetbrains.anko.doAsync

class SecondActivity : AppCompatActivity() {
    val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val networkListener = NetworkHelper.newInstance()

        doAsync {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (networkListener.isWifiConnected(context))
                    icone_wifi.background = resources.getDrawable(R.drawable.ic_network_on)
                else
                    icone_wifi.background = resources.getDrawable(R.drawable.ic_network_off)

                if (networkListener.isMobileDataConnected(context))
                    icone_tres_g.background = resources.getDrawable(R.drawable.ic_network_cell_on)
                else
                    icone_tres_g.background = resources.getDrawable(R.drawable.ic_network_cell_off)
            }


            if (NetworkHelper.isInternetConnected(context))
                box.setBackgroundColor(resources.getColor(R.color.green))
            else
                box.setBackgroundColor(resources.getColor(R.color.red))

        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            if (networkListener.isWifiConnected(context))
//                icone_wifi.background = resources.getDrawable(R.drawable.ic_network_on)
//            else
//                icone_wifi.background = resources.getDrawable(R.drawable.ic_network_off)
//
//            if (networkListener.isMobileDataConnected(context))
//                icone_tres_g.background = resources.getDrawable(R.drawable.ic_network_cell_on)
//            else
//                icone_tres_g.background = resources.getDrawable(R.drawable.ic_network_cell_off)
//        }
//
//
//        if (NetworkHelper.isInternetConnected(this))
//            box.setBackgroundColor(resources.getColor(R.color.green))
//        else
//            box.setBackgroundColor(resources.getColor(R.color.red))

    }
}
