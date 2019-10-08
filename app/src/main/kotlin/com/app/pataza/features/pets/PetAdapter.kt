package com.app.pataza.features.pets

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

class PetAdapter
@Inject constructor() : RecyclerView.Adapter<PetAdapter.ViewHolder>() {

    internal var collection: List<PetView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (PetView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.item_pet))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(petView: PetView, clickListener: (PetView, Navigator.Extras) -> Unit) {
            itemView.moviePoster.loadFromUrl(petView.photos[0].url)
            itemView.setOnClickListener { clickListener(petView, Navigator.Extras(itemView.imgPet)) }
        }
    }
}
