package com.app.pataza.core.functional.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import com.app.pataza.R

object ErrorDialog {

    fun create(context: Context?, op: () -> Unit, errorText: String? = null, buttonText: String? = null) {
        val alertBuilder = AlertDialog.Builder(context)
        val alertDialog = alertBuilder.create()
        alertDialog.setCancelable(false)

        val view = alertDialog.layoutInflater.inflate(R.layout.dialog_one_option, null)
        alertDialog.setView(view)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        val txt = alertDialog.findViewById<TextView>(R.id.txtError)
        errorText?.let { txt.text = it }
        val btn = alertDialog.findViewById<Button>(R.id.btnOk)
        buttonText?.let { btn.text = it }
        btn.setOnClickListener {
            alertDialog?.let {
                op()
                it.dismiss()
            }
        }
    }

}