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
import kotlinx.android.synthetic.main.dialog_multiple_list.view.*
import kotlinx.android.synthetic.main.item_multiple.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class PickerMultipleFragment : DialogFragment() {
    private var listener: Callback? = null
    private var list = ArrayList<Int>()

    companion object {
        fun newInstance(list: ArrayList<String>): PickerMultipleFragment {
            val fragment = PickerMultipleFragment()
            val bundle = Bundle()
            bundle.putStringArrayList("list", list)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_multiple_list, container, false)
        arguments?.let {
            v.rvItems.layoutManager = LinearLayoutManager(context)
            val adapter = ItemsAdapter()
            v.rvItems.adapter = adapter
            it.getStringArrayList("list")?.let { list ->
                adapter.collection = list.toList()
            }
            adapter.clickListener = { position, status ->
                if (status) list.add(position) else list.remove(position)
            }
            v.btPick.setOnClickListener {
                listener?.onItemMultiple(list)
                dismiss()
            }
        }
        return v
    }

    fun setListener(listener: Callback) {
        this.listener = listener
    }

    interface Callback {
        fun onItemMultiple(multiple: ArrayList<Int>)
    }

    class ItemsAdapter
    @Inject constructor() : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

        internal var collection: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
            notifyDataSetChanged()
        }

        internal var clickListener: (Int, Boolean) -> Unit = { _, _ -> }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ViewHolder(parent.inflate(R.layout.item_multiple))

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
                viewHolder.bind(collection[position], position, clickListener)

        override fun getItemCount() = collection.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: String, position: Int, clickListener: (Int, Boolean) -> Unit) {
                itemView.tvMultiple.text = item
                itemView.cbMultiple.setOnCheckedChangeListener { _, b ->
                    clickListener(position, b)
                }
            }
        }
    }

}