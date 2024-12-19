package com.example.gouvernorats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch

class HotelDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val hotelId = intent.getLongExtra("hotelId", -1L)

        if (hotelId != -1L) {
            lifecycleScope.launch {
                try {
                    // Récupération des détails de l'hébergement
                    val response = ApiClient.apiService.getHebergementById(hotelId)
                    if (response.isSuccessful) {
                        val hotel = response.body()
                        hotel?.let {
                            // Mise à jour des informations de l'hôtel
                            val hotelNameTextView: TextView = findViewById(R.id.hotelNameTextView)
                            hotelNameTextView.text = hotel.nom

                            val hotelPriceTextView: TextView = findViewById(R.id.hotelPriceTextView)
                            hotelPriceTextView.text = "${hotel.prix} TND"

                            val hotelLocationTextView: TextView = findViewById(R.id.hotelLocationTextView)
                            hotelLocationTextView.text = hotel.localisation

                            val viewPager: ViewPager2 = findViewById(R.id.viewPagerAlbum)

                            // Récupération des images de l'hébergement
                            val imagesResponse = ApiClient.apiService.getImagesByHebergementId(hotelId)
                            if (imagesResponse.isSuccessful) {
                                val images = imagesResponse.body() ?: emptyList()
                                hotel.images = images

                                // Vérifiez que la liste d'images n'est pas vide
                                if (images.isNotEmpty()) {
                                    // Configuration de l'adaptateur du ViewPager
                                    val imageAdapter = PhotoPagerAdapter(images)
                                    viewPager.adapter = imageAdapter

                                    // Affiche la première image dans le ViewPager
                                    viewPager.setCurrentItem(0, false) // Affiche la première image dès le début
                                } else {
                                    Toast.makeText(this@HotelDetailsActivity, "Aucune image disponible", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@HotelDetailsActivity, "Erreur lors de la récupération des images", Toast.LENGTH_SHORT).show()
                            }

                            // Gestion des autres services (Wi-Fi et Parking)
                            val wifiTextView: TextView = findViewById(R.id.wifiText)
                            wifiTextView.text = if (hotel.wifi) "Wi-Fi disponible" else "Wi-Fi non disponible"

                            val parkingTextView: TextView = findViewById(R.id.parkingText)
                            parkingTextView.text = if (hotel.parking) "Parking disponible" else "Parking non disponible"

                            val entertaiment: TextView = findViewById(R.id.spa)
                            entertaiment.text = if (hotel.entertaiment) "Spa et centre de bien-être" else "Spa et centre de bien-être non disponible"

                            val piscines: TextView = findViewById(R.id.piscines)
                            piscines.text = if (hotel.piscines) "Piscine disponible" else "Piscine non disponible"

                            val serviceChambre: TextView = findViewById(R.id.roomServiceText)
                            serviceChambre.text = if (hotel.serviceChambre) "Service en chambre 24h/24 disponible" else "Service en chambre 24h/24non disponible"

                            val restauration: TextView = findViewById(R.id.restaurantText)
                            restauration.text = if (hotel.restauration) "Restaurant gastronomique disponible" else "Restaurant gastronomique non disponible"

                            // Bouton de réservation
                            val btnReserve: Button = findViewById(R.id.btnReserve)
                            btnReserve.setOnClickListener {
                                val intent = Intent(this@HotelDetailsActivity, ReservationActivity::class.java)
                                intent.putExtra("hotelId", hotelId.toString()) // Convertir Long en String
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(this@HotelDetailsActivity, "Erreur de récupération des détails", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@HotelDetailsActivity, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "ID d'hôtel invalide", Toast.LENGTH_SHORT).show()
        }
    }
}
