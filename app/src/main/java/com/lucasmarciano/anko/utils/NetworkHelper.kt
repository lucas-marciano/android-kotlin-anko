package com.lucasmarciano.anko.utils

import android.support.v4.app.Fragment
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.content.IntentFilter
import android.net.ConnectivityManager

/**
 *
 * Creation 02/07/2018
 * @author lucasmarciano
 * @version 0.0.0
 */
class NetworkHelper : Fragment() {
    companion object {
        val CHECK_INTERNET = "network_connection"

        fun isInternetConnected(context: Context): Boolean {
            val connec = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

            if (wifi != null && wifi.isConnected) {
                return true
            } else if (mobile != null && mobile.isConnected) {
                return true
            }
            return false
        }

        fun newInstance() = NetworkHelper()
    }


    private var mActivity: Activity? = null
    var mAlertDialog: AlertDialog? = null

    private val onNotice = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.hasExtra(CHECK_INTERNET) && !intent.getBooleanExtra(CHECK_INTERNET, true)) {
                mActivity?.let {
                    showAlertDialog(it, "Internet Connection",
                            "No internet connection available.\n\n" + "Please check your internet connection and try again.")
                }
            } else {
                if (mAlertDialog != null && mAlertDialog!!.isShowing()) {
                    mAlertDialog!!.dismiss()
                    mAlertDialog = null
                }
            }
        }
    }

    fun NetworkHelper() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mActivity = activity
    }

    override fun onResume() {
        super.onResume()
        val iff = IntentFilter(CHECK_INTERNET)
        mActivity?.let { LocalBroadcastManager.getInstance(it).registerReceiver(onNotice, iff) }
        if (!mActivity?.let { isInternetConnected(it) }!!) {
            mActivity?.let {
                showAlertDialog(it, "Internet Connection",
                        "No internet connection available.\n\n" + "Please check your internet connection and try again.")
            }
        }

    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun onPause() {
        super.onPause()
        mActivity?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(onNotice) }
    }

    fun showAlertDialog(context: Context, title: String, message: String) {
        if (mAlertDialog != null && mAlertDialog!!.isShowing) {
            return  //already showing
        } else if (mAlertDialog != null) {
            mAlertDialog!!.dismiss()
            mAlertDialog = null
        }
        mAlertDialog = AlertDialog.Builder(context).create()

        // Setting Dialog Title
        mAlertDialog!!.setTitle(title)

        // Setting Dialog Message
        mAlertDialog!!.setMessage(message)

        // Setting OK Button
        mAlertDialog!!.setButton(DialogInterface.BUTTON_POSITIVE,
                getString(android.R.string.ok),
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                    mAlertDialog = null
                })

        // Showing Alert Message
        mAlertDialog!!.show()
    }

    fun isWifiConnected(context: Context): Boolean {
        val connec = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return wifi != null && wifi.isConnected

    }

    fun isMobileDataConnected(context: Context): Boolean {
        val connec = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return mobile != null && mobile.isConnected

    }
}