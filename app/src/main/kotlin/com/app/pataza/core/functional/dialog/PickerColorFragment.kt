package com.app.pataza.core.functional.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.pataza.R
import com.app.pataza.core.extension.inflate
import kotlinx.android.synthetic.main.dialog_color_list.view.*
import kotlinx.android.synthetic.main.dialog_single_list.view.rvItems
import kotlinx.android.synthetic.main.item_color.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class PickerColorFragment : DialogFragment() {
    private var listener: Callback? = null
    private var list = ArrayList<Int>()

    companion object {
        fun newInstance(list: ArrayList<Int>): PickerColorFragment {
            val fragment = PickerColorFragment()
            val bundle = Bundle()
            bundle.putIntegerArrayList("list", list)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_color_list, container, false)
        arguments?.let {
            v.rvItems.layoutManager = LinearLayoutManager(context)
            val adapter = ItemsAdapter()
            v.rvItems.adapter = adapter
            it.getIntegerArrayList("list")?.let { list ->
                adapter.collection = list.toList()
            }
            adapter.clickListener = { color, status ->
                if (status) list.add(color) else list.remove(color)
            }
            v.btPick.setOnClickListener {
                listener?.onItemColor(list)
                dismiss()
            }
        }
        return v
    }

    fun setListener(listener: Callback) {
        this.listener = listener
    }

    interface Callback {
        fun onItemColor(colors: ArrayList<Int>)
    }

    class ItemsAdapter
    @Inject constructor() : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

        internal var collection: List<Int> by Delegates.observable(emptyList()) { _, _, _ ->
            notifyDataSetChanged()
        }

        internal var clickListener: (Int, Boolean) -> Unit = { _, _ -> }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ViewHolder(parent.inflate(R.layout.item_color))

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
                viewHolder.bind(collection[position], clickListener)

        override fun getItemCount() = collection.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: Int, clickListener: (Int, Boolean) -> Unit) {
                itemView.ivColor.setBackgroundResource(item)
                itemView.cbColor.setOnCheckedChangeListener { _, b ->
                    clickListener(item, b)
                }
            }
        }
    }

}