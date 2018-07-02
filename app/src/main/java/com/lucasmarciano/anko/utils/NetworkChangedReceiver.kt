/*
 * Developed by: Inatel Competence Center
 * Copyright 2018, NONUS
 * All rights are reserved. Reproduction in whole or part is
 * prohibited without the written consent of the copyright owner.
 */
package com.lucasmarciano.anko.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager



/**
 *
 * Creation 02/07/2018
 * @author lucasmarciano
 * @version 0.0.0
 */
class NetworkChangedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        val intent = Intent(NetworkHelper.CHECK_INTERNET)
        intent.putExtra(NetworkHelper.CHECK_INTERNET, NetworkHelper.isInternetConnected(context!!))
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}