package com.example.gouvernorats

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HotelAdapter(
    private val hotels: List<HotelPackage>,
    private val onItemClick: (HotelPackage) -> Unit
) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.hotel_name)
        val locationTextView: TextView = view.findViewById(R.id.hotel_location)
        val priceTextView: TextView = view.findViewById(R.id.hotel_price)
        val imageView: ImageView = view.findViewById(R.id.hotel_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gouvernorat, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotels[position]
        holder.nameTextView.text = hotel.nom
        holder.locationTextView.text = hotel.localisation
        holder.priceTextView.text = "${hotel.prix} TND"

        // Vérifie si la liste d'images n'est pas nulle et non vide
        if (!hotel.images.isNullOrEmpty()) {
            val imageBase64 = hotel.images[0]
            val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)

            Glide.with(holder.itemView.context)
                .asBitmap()
                .load(imageBytes)
                .into(holder.imageView)
        } else {
            // Image par défaut si aucune image n'est présente
            holder.imageView.setImageResource(R.drawable.hotel3)
        }

        holder.itemView.setOnClickListener { onItemClick(hotel) }
    }

    override fun getItemCount(): Int = hotels.size
}
