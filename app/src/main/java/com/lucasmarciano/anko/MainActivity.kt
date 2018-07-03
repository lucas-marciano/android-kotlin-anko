package com.lucasmarciano.anko

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lucasmarciano.anko.utils.PermissionHelper
import org.jetbrains.anko.design.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), AnkoLogger  {
    private val PERMISSIONS_REQUEST_CODE = 1
    private var mPermissions: PermissionHelper? = null
    private var permissions = arrayOf(
            Manifest.permission.CALL_PHONE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPermissions = PermissionHelper(this, permissions)

    }

    fun showToast(view: View) {
        toast("Hi there!")
    }

    fun showSnackBar(view: View) {
        snackbar(view, "Wow, such duration")
    }

    fun showAlert(view: View) {
        alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
            yesButton { toast("Yes!!!…") }
            noButton { toast("Noooooooo…") }
        }.show()
    }

    fun showSelector(view: View) {
        val countries = listOf("Call", "Send Text", "Browser", "Share", "E-mail")

        selector("Where are you from?", countries) { _, i ->
            when (i) {
                0 -> permissionCall()
                1 -> sendSMS("9999999999", "Texto para SMS")
                2 -> browse("https://google.com")
                3 -> share("Corpo", "Subject")
                4 -> email("email@email.com", "Subject", "Corpo")
            }
        }
    }

    fun openIntent(view: View) {
        startActivity(intentFor<SecondActivity>("id" to 5, "second" to 2).singleTop())
    }

    fun permissionCall() {
        if (!mPermissions?.checkPermissions()!!) {
            mPermissions!!.requestPermissions(PERMISSIONS_REQUEST_CODE)
        }
        makeCall("9999999999")
    }

    fun saveLog(view: View) {
        toast("Log clicked")

        info("Info")
        verbose("Verbose")
        debug("Debug")
        warn("Warn")
        error("Erro")
        wtf("WTF")
    }
}
