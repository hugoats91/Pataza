package com.app.pataza.core.functional.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.pataza.R

class ForgetPasswordDialogFragment : DialogFragment() {
    private var listener: Callback? = null

    companion object {
        fun newInstance(): ForgetPasswordDialogFragment {
            return ForgetPasswordDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_forget_password, container, false)
        arguments?.let {

        }
        return v
    }

    fun setListener(listener: Callback) {
        this.listener = listener
    }

    interface Callback {
        fun onItem(position: Int)
    }

}