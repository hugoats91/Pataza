package com.app.pataza.core.functional.view

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.app.pataza.R
import com.app.pataza.core.platform.BaseFragment
import kotlinx.android.synthetic.main.view_edittext.view.*
import java.lang.Exception

class EditTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var list = ArrayList<String>()
    var fragment: BaseFragment? = null
    var inputType = 0
    var action = false
    var listener: Callback? = null

    companion object {
        const val INPUT_TYPE_DEFAULT = 1
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_edittext, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.EditTextView, 0, 0)
            etText.hint = typedArray.getString(R.styleable.EditTextView_hint)
            etHint.text = typedArray.getString(R.styleable.EditTextView_hint)
            action = typedArray.getBoolean(R.styleable.EditTextView_action, false)
            etText.isFocusableInTouchMode = !action
            inputType = typedArray.getInt(R.styleable.EditTextView_input_type, INPUT_TYPE_DEFAULT)
            typeEditText()
            typedArray.recycle()
        }

        if(action){
           etText.setOnClickListener {
               listener?.onClick()
           }
        }

        etText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    if (it.isEmpty()) {
                        etHint.visibility = View.GONE
                    } else {
                        etHint.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    fun getText() = etText.text.toString().trim()

    fun getInt(): Int?{
        return try {
            etText.text.toString().trim().toInt()
        }catch (e: Exception){
            null
        }
    }

    fun getDouble(): Double?{
        return try {
            etText.text.toString().trim().toDouble()
        }catch (e: Exception){
            null
        }
    }

    fun setText(text: String){
        etText.setText(text)
    }

    interface Callback{
        fun onClick()
    }

    private fun typeEditText() {
        when (inputType) {
            1 -> etText.inputType = InputType.TYPE_CLASS_TEXT
            2 -> etText.inputType = InputType.TYPE_CLASS_NUMBER
            3 -> etText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

}