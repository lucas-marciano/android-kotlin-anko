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

    /**
     * Show a toast
     * @see [http://github.com/Kotlin/anko/wiki/Anko-Commons-%E2%80%93-Dialogs#toasts]
      */
    fun showToast(view: View) {
        toast("Hi there!")
    }

    /**
     * Show a snackbar.
     * @see [https://github.com/Kotlin/anko/wiki/Anko-Commons-%E2%80%93-Dialogs#snackbars]
     */
    fun showSnackBar(view: View) {
        snackbar(view, "Wow, such duration")
    }

    /**
     * Show a alert with buttons.
     * @see [https://github.com/Kotlin/anko/wiki/Anko-Commons-%E2%80%93-Dialogs#alerts]
     */
    fun showAlert(view: View) {
        alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
            yesButton { toast("Yes!!!…") }
            noButton { toast("Nooooooo…") }
        }.show()
    }

    /**
     * Show alert with selector.
     * @see [https://github.com/Kotlin/anko/wiki/Anko-Commons-%E2%80%93-Dialogs#selectors]
     */
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

    /**
     * Open new activity.
     * @see [https://github.com/Kotlin/anko/wiki/Anko-Commons-%E2%80%93-Intents]
     */
    fun openIntent(view: View) {
        startActivity(intentFor<SecondActivity>("id" to 5, "second" to 2).singleTop())
    }

    /**
     * Logcat controller.
     * @see [https://github.com/Kotlin/anko/wiki/Anko-Commons-%E2%80%93-Logging]
     */
    fun saveLog(view: View) {
        toast("Log clicked")

        info("Info")
        verbose("Verbose")
        debug("Debug")
        warn("Warn")
        error("Erro")
        wtf("WTF")
    }


    /**
     * Method that check ther permission of to use the phone to calling.
     */
    private fun permissionCall() {
        if (!mPermissions?.checkPermissions()!!) {
            mPermissions!!.requestPermissions(PERMISSIONS_REQUEST_CODE)
        }
        makeCall("9999999999")
    }
}
