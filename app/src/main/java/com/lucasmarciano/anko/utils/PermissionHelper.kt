/*
 * Developed by: Inatel Competence Center
 * Copyright 2018, NONUS
 * All rights are reserved. Reproduction in whole or part is
 * prohibited without the written consent of the copyright owner.
 */
package com.lucasmarciano.anko.utils

import android.os.Build
import android.app.Activity
import java.util.*
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat
import android.util.Log


/**
 * Class to check the permissions in Android M and superior.
 *
 * Creation 02/07/2018
 * @author lucasmarciano
 * @version 0.0.0
 */
class PermissionHelper(val mContext: Activity, val mRequiredPermissions: Array<String>) {
    private val TAG = "PermissionHelper"

    private val mPermissionsToRequest = ArrayList<String>()

    /**
     * Checks if all the required permissions are granted.
     * @return true if all the required permissions are granted, otherwise false
     */
    fun checkPermissions(): Boolean {
        if(isMNC())
            return true

        for (permission in mRequiredPermissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionsToRequest.add(permission)
            }
        }

        return mPermissionsToRequest.isEmpty()

    }

    /**
     * Requests the missing permissions.
     * The activity from which this method is called has to implement
     * [Activity.onRequestPermissionsResult]
     * and then, inside it, it has to call the method
     * [PermissionHelper.checkAllRequiredPermissionsGranted] to check that all the
     * requested permissions are granted by the user
     * @param requestCode request code used by the activity
     */
    fun requestPermissions(requestCode: Int) {
        val request = mPermissionsToRequest.toArray(arrayOfNulls<String>(mPermissionsToRequest.size))

        val log = StringBuilder()
        log.append("Requesting permissions:\n")

        for (permission in request) {
            log.append(permission).append("\n")
        }

        Log.i(TAG, log.toString())

        ActivityCompat.requestPermissions(mContext, request, requestCode)
    }

    /**
     * Method to call inside
     * [Activity.onRequestPermissionsResult], to check if the
     * required permissions are granted.
     * @param permissions permissions requested
     * @param grantResults results
     * @return true if all the required permissions are granted, otherwise false
     */
    fun checkAllRequiredPermissionsGranted(permissions: Array<String>?, grantResults: IntArray?): Boolean {
        if(isMNC())
            return true

        if (permissions == null || permissions.isEmpty() || grantResults == null || grantResults.isEmpty())
            return false

        val perms = LinkedHashMap<String, Int>()

        for (i in permissions.indices) {
            if (!perms.containsKey(permissions[i]) || perms.containsKey(permissions[i])
                    && perms[permissions[i]] == PackageManager.PERMISSION_DENIED)
                perms[permissions[i]] = grantResults[i]
        }

        for (entry in perms.entries) {
            if (entry.value != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }

        return true
    }

    /**
     * Method to check if the version of the Android is iqual or superior of a Android M
     */
    fun isMNC(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

}