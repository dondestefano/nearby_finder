package com.example.nearby_finder.adapters

import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.nearby_finder.PlacesViewModel
import com.example.nearby_finder.data.PlaceItem
import com.example.nearby_finder.managers.SharedPrefHelper
import com.example.nearby_finder.util.Encryption
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Picasso
            .get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .into(view)
    }
}

@BindingAdapter("decryptedName")
fun decryptName(view: TextView, placeItem: PlaceItem) {
        val encrypted = Base64.decode(placeItem.name, Base64.NO_WRAP)
        val salt = Base64.decode(placeItem.salt, Base64.NO_WRAP)

        val iv = Base64.decode(placeItem.iv, Base64.NO_WRAP)

        val decrypted = Encryption.decrypt(
                hashMapOf("iv" to iv, "salt" to salt, "encrypted" to encrypted), SharedPrefHelper.encryptionPassword)

        decrypted?.let {
            view.text = String(it, Charsets.UTF_8)
        }
}