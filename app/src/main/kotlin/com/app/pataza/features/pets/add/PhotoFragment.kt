package com.app.pataza.features.pets.add

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.DialogFragment
import com.app.pataza.R

class PhotoFragment : DialogFragment() {

    var listener: ActionPhoto? = null
    private var btnCamera: Group? = null
    private var btnGallery: Group? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_photo, null)
        btnCamera = view.findViewById(R.id.btn_camera)
        btnGallery = view.findViewById(R.id.btn_gallery)
        val alert = AlertDialog.Builder(activity!!)
        alert.setView(view)

        this.btnCamera!!.setAllOnClickListener(View.OnClickListener {
            listener?.onCamera()
            dismiss()
        })

        this.btnGallery!!.setAllOnClickListener(View.OnClickListener {
            listener?.onGallery()
            dismiss()
        })

        return alert.create()
    }

    interface  ActionPhoto{
        fun onCamera()
        fun onGallery()
    }

    private fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }
}