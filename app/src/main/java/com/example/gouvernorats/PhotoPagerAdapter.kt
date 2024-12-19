package com.example.gouvernorats

import android.view.LayoutInflater
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
class PhotoPagerAdapter(private val images: List<String>) : RecyclerView.Adapter<PhotoPagerAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val imagePath = images[position]

        // Vérification de si l'image est Base64 ou URL
        if (imagePath.startsWith("http")) {
            // C'est une URL
            Glide.with(holder.itemView.context)
                .load(imagePath)
                .into(holder.imageView)
        } else {
            // C'est probablement du Base64
            try {
                val imageBytes = Base64.decode(imagePath, Base64.DEFAULT)
                Glide.with(holder.itemView.context)
                    .asBitmap()
                    .load(imageBytes)
                    .into(holder.imageView)
            } catch (e: IllegalArgumentException) {
                Log.e("PhotoPagerAdapter", "Erreur lors du décodage Base64 : ${e.message}")
            }
        }
    }

    override fun getItemCount(): Int = images.size
}
