package com.app.pataza.core.functional.view

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.app.pataza.R
import com.app.pataza.core.functional.dialog.PickerColorFragment
import com.app.pataza.core.functional.dialog.PickerFragment
import com.app.pataza.core.functional.dialog.PickerMultipleFragment
import com.app.pataza.core.platform.BaseFragment
import kotlinx.android.synthetic.main.view_spinner.view.*

class SpinnerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes), PickerFragment.Callback, PickerColorFragment.Callback, PickerMultipleFragment.Callback {

    var list = ArrayList<String>()
    var listColor: ArrayList<Int>? = null
    var fragment: BaseFragment? = null
    var type: Int = TYPE_DEFAULT
    var colorListener: ColorCallback? = null
    var multipleListener: MultipleCallback? = null
    var singleListener: SingleCallback? = null

    companion object{
        const val TYPE_DEFAULT = 1
        const val TYPE_MULTIPLE = 2
        const val TYPE_COLOR = 3
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_spinner, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SpinnerView, 0, 0)
            etSpinner.hint = typedArray.getString(R.styleable.SpinnerView_hint)
            etHint.text = typedArray.getString(R.styleable.SpinnerView_hint)
            type = typedArray.getInt(R.styleable.SpinnerView_type, TYPE_DEFAULT)
            typedArray.recycle()
        }

        typeAction()

        etSpinner.addTextChangedListener(object : TextWatcher {

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

    private fun typeAction(){
        when(type){
            TYPE_DEFAULT -> {
                vAction.setOnClickListener {
                    fragment?.let {
                        val frag = PickerFragment.newInstance(ArrayList(list))
                        frag.setListener(this)
                        frag.show(it.fragmentManager, "list_dialog")
                    }
                }
            }
            TYPE_MULTIPLE -> {
                vAction.setOnClickListener {
                    fragment?.let {
                        val frag = PickerMultipleFragment.newInstance(ArrayList(list))
                        frag.setListener(this)
                        frag.show(it.fragmentManager, "list_dialog")
                    }
                }
            }
            TYPE_COLOR -> {
                vAction.setOnClickListener {
                    listColor?.let {list ->
                        fragment?.let {
                            val frag = PickerColorFragment.newInstance(ArrayList(list))
                            frag.setListener(this)
                            frag.show(it.fragmentManager, "list_dialog")
                        }
                    }
                }
            }
        }
    }

    fun <T> setupData(fragment: BaseFragment, list: ArrayList<T>){
        this.fragment = fragment
        if(list.isNotEmpty()){
            when(list[0]){
                is Int -> {
                    listColor = ArrayList(list.map { it as Int })
                }
                is String -> {
                    this.list = ArrayList(list.map { it as String })
                }
            }
        }
    }

    fun setText(text: String) {
        etSpinner.setText(text)
    }

    fun getText() = etSpinner.text.toString().trim()

    override fun onItem(position: Int) {
        etSpinner.setText(list[position])
        singleListener?.onSingleSelected(position)
    }

    override fun onItemMultiple(multiple: ArrayList<Int>) {
        var chain = ""
        list.forEachIndexed { index, s ->
            if(multiple.any { it == index }){
                if(chain.isEmpty()){
                    chain += s
                }else{
                    chain += " - $s"
                }

            }
        }
        etSpinner.setText(chain)
        multipleListener?.onMultipleSelected(multiple)
    }

    override fun onItemColor(colors: ArrayList<Int>) {
        if(colors.isNotEmpty()){
            if(colors.size==1){
                etSpinner.setText(context.getString(R.string.tx_pick_color, "${colors.size} "))
            }else{
                etSpinner.setText(context.getString(R.string.tx_pick_colors, "${colors.size} "))
            }
            colorListener?.onColorSelected(colors)
        }
    }
    
    interface SingleCallback{
        fun onSingleSelected(position: Int)
    }
    
    interface MultipleCallback{
        fun onMultipleSelected(positions: ArrayList<Int>)
    }

    interface ColorCallback{
        fun onColorSelected(positions: ArrayList<Int>)
    }


}