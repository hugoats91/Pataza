package com.app.pataza.features.profile.pets

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pataza.R
import com.app.pataza.core.extension.inflate
import com.app.pataza.core.extension.loadFromUrl
import com.app.pataza.core.navigation.Navigator
import com.app.pataza.features.pets.PetView
import kotlinx.android.synthetic.main.item_my_pet.view.*
import kotlinx.android.synthetic.main.item_pet.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class MyPetsAdapter
@Inject constructor() : RecyclerView.Adapter<MyPetsAdapter.ViewHolder>() {

    internal var collection: List<PetView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (PetView, Navigator.Extras) -> Unit = { _, _ -> }
    internal var removeClickListener: (PetView) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.item_my_pet))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener, removeClickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(petView: PetView, clickListener: (PetView, Navigator.Extras) -> Unit, removeClickListener: (PetView) -> Unit) {
            if (petView.photos.isNotEmpty()) {
                itemView.ivPet.loadFromUrl(petView.photos[0].url)
            }
            itemView.tvTitle.text = petView.name
            itemView.tvDescription.text = petView.description
            itemView.btRemove.setOnClickListener { removeClickListener(petView) }
            itemView.setOnClickListener { clickListener(petView, Navigator.Extras(itemView.imgPet)) }
        }
    }
}
