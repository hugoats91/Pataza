package com.app.pataza.features.profile

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pataza.R
import com.app.pataza.core.extension.inflate
import com.app.pataza.core.extension.loadFromUrl
import com.app.pataza.core.navigation.Navigator
import kotlinx.android.synthetic.main.item_pet.view.*
import kotlinx.android.synthetic.main.row_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class RequestAdapter
@Inject constructor() : RecyclerView.Adapter<RequestAdapter.ViewHolder>() {

    internal var collection: List<RequestView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (RequestView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.item_pet))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(requestView: RequestView, clickListener: (RequestView, Navigator.Extras) -> Unit) {
            itemView.moviePoster.loadFromUrl(requestView.photoPet)
            itemView.setOnClickListener { clickListener(requestView, Navigator.Extras(itemView.imgPet)) }
        }
    }
}