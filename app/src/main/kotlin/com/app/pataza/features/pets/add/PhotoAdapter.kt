package com.app.pataza.features.pets.add

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.pataza.R
import com.app.pataza.features.pets.PetView
import kotlin.properties.Delegates

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.AddPetViewHolder>() {

    internal var collection: List<Bitmap> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_photo,
                parent, false)
        return AddPetViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: AddPetViewHolder, position: Int) {
        val petImageItem = collection[position]
        holder.addPetListItem(petImageItem)
    }

    class AddPetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var petImage = itemView.findViewById<AppCompatImageView>(R.id.image_pet)

        fun addPetListItem(bitmap: Bitmap) {
            petImage.setImageBitmap(bitmap)
        }
    }
}