package com.app.pataza.data.models

import com.app.pataza.features.pets.PhotoView

data class Photo(val photoId: String, val url: String){
    fun toPhotoView() = PhotoView(photoId, url)
}