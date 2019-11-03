package com.app.pataza.core.functional.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.app.pataza.core.util.Utils
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private var listener: Callback? = null
    private var template = ""

    companion object {
        fun newInstance(initial: Date, template: String = "dd/MM/yyyy"): DatePickerFragment {
            val fragment = DatePickerFragment()
            val bundle = Bundle()
            bundle.putLong("initial", initial.time)
            bundle.putString("template", template)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        arguments?.let {
            c.time = Date(it.getLong("initial"))
            template = it.getString("template") ?: ""
        }
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val format = Utils.formatDate(template, year, month, day)
        listener?.onDate(format)
    }

    fun setListener(listener: Callback) {
        this.listener = listener
    }

    interface Callback {
        fun onDate(result: String)
    }
}