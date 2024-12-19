package com.example.gouvernorats

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HotelAdapter
    private lateinit var searchField: AutoCompleteTextView
    private lateinit var searchButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchField = findViewById(R.id.destination) // Référence au champ de recherche
        searchButton = findViewById(R.id.search_button) // Référence au bouton de recherche

        // Configurer la recherche
        setupSearch()

        // Charger tous les hôtels par défaut
        fetchHotels()
    }

    private fun setupSearch() {
        // Ajouter un clic listener sur le bouton de recherche
        searchButton.setOnClickListener {
            val localisation = searchField.text.toString().trim()
            if (localisation.isNotEmpty()) {
                searchHotelsByLocalisation(localisation)
            } else {
                Toast.makeText(this, "Veuillez entrer une localisation.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchHotels() {
        lifecycleScope.launch {
            try {
                // Appel pour récupérer tous les hôtels disponibles
                val response = ApiClient.apiService.getHebergementsDisponibles()
                if (response.isSuccessful) {
                    val hotels = response.body() ?: emptyList()

                    // Appel API pour récupérer les images pour chaque hôtel
                    for (hotel in hotels) {
                        val imagesResponse = ApiClient.apiService.getImagesByHebergementId(hotel.id)
                        if (imagesResponse.isSuccessful) {
                            val images = imagesResponse.body() ?: emptyList()
                            hotel.images = images
                        }
                    }

                    // Mise à jour de l'adapter avec la liste d'hôtels
                    updateRecyclerView(hotels)
                } else {
                    Toast.makeText(this@MainActivity, "Erreur du serveur : ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchHotelsByLocalisation(localisation: String) {
        lifecycleScope.launch {
            try {
                // Appel pour rechercher les hôtels par localisation
                val response = ApiClient.apiService.searchHebergementsByLocalisation(localisation)
                if (response.isSuccessful) {
                    val hotels = response.body() ?: emptyList()

                    // Mise à jour de l'adapter avec la liste d'hôtels filtrée
                    updateRecyclerView(hotels)
                } else {
                    Toast.makeText(this@MainActivity, "Aucun hôtel trouvé pour cette localisation.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateRecyclerView(hotels: List<HotelPackage>) {
        adapter = HotelAdapter(hotels) { hotel ->
            val intent = Intent(this@MainActivity, HotelDetailsActivity::class.java).apply {
                putExtra("hotelId", hotel.id)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
