package com.example.gouvernorats

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ReservationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chambreAdapter: ChambreAdapter
    private val selectedChambresIds = mutableListOf<Long>() // Liste des chambres sélectionnées


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservation)

      // Récupérer l'ID de l'hôtel
        val hotelId = intent.getStringExtra("hotelId")?.toLongOrNull()
        if (hotelId == null) {
            Toast.makeText(this, "ID d'hôtel invalide.", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        // Champs de date
        val editTextDateArrivee = findViewById<EditText>(R.id.editTextDateArrivee)
        val editTextDateDepart = findViewById<EditText>(R.id.editTextDateDepart)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fun showDatePickerDialog(editText: EditText) {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    editText.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        editTextDateArrivee.setOnClickListener { showDatePickerDialog(editTextDateArrivee) }
        editTextDateDepart.setOnClickListener { showDatePickerDialog(editTextDateDepart) }

        // Initialisation du RecyclerView
        recyclerView = findViewById(R.id.recyclerViewChambres)
        recyclerView.layoutManager = LinearLayoutManager(this)

        chambreAdapter = ChambreAdapter(emptyList()) { selectedRoomIds ->
            selectedChambresIds.clear()
            selectedChambresIds.addAll(selectedRoomIds)
            Log.d("ReservationActivity", "Chambres sélectionnées : $selectedChambresIds")
        }
        recyclerView.adapter = chambreAdapter

        // Charger les chambres disponibles
        loadChambres(hotelId)

        // Bouton pour soumettre la réservation
        findViewById<Button>(R.id.btnBookRoom).setOnClickListener {
            if (selectedChambresIds.isNotEmpty()) {
                createReservation(hotelId)
            } else {
                Toast.makeText(this, "Veuillez sélectionner des chambres.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun loadChambres(hebergementId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.getChambresByHebergementId(hebergementId)
                if (response.isSuccessful) {
                    val chambres = response.body() ?: emptyList()
                    Log.d("ReservationActivity", "Chambres reçues: $chambres")
                    withContext(Dispatchers.Main) {
                        if (chambres.isNotEmpty()) {
                            chambreAdapter.updateData(chambres)
                        } else {
                            Toast.makeText(
                                this@ReservationActivity,
                                "Aucune chambre disponible.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Log.e("ReservationActivity", "Erreur API: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ReservationActivity,
                            "Erreur lors du chargement des chambres.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("ReservationActivity", "Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ReservationActivity,
                        "Une erreur est survenue.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun createReservation(hebergementId: Long) {
        val lastName = findViewById<EditText>(R.id.editTextLastName).text.toString()
        val email = findViewById<EditText>(R.id.editTextEmail).text.toString()
        val phone = findViewById<EditText>(R.id.editTextPhone).text.toString()
        val dateArrivee = findViewById<EditText>(R.id.editTextDateArrivee).text.toString()
        val dateDepart = findViewById<EditText>(R.id.editTextDateDepart).text.toString()
        val nombreAdultes =
            findViewById<EditText>(R.id.editTextAdultes).text.toString().toIntOrNull() ?: 0
        val nombreEnfants =
            findViewById<EditText>(R.id.editTextEnfants).text.toString().toIntOrNull() ?: 0

        if (lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || dateArrivee.isEmpty() || dateDepart.isEmpty()) {
            Toast.makeText(
                this,
                "Veuillez remplir tous les champs obligatoires.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val clientData = ClientData(
            nom = lastName,
            email = email,
            telephone = phone
        )

        val reservation = ReservationPackage(
            clientData = clientData,
            chambreIds = selectedChambresIds,
            date_arrivee = dateArrivee,
            date_depart = dateDepart,
            adultes = nombreAdultes,
            enfants = nombreEnfants
        )

        // Log des données envoyées
        Log.d("createReservation", "Données envoyées : $reservation")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.createReservation(hebergementId,reservation)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ReservationActivity,
                            "Réservation réussie!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                } else {
                    Log.e(
                        "createReservation",
                        "Erreur API: Code ${response.code()} - ${response.errorBody()?.string()}"
                    )
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ReservationActivity,
                            "Erreur lors de la réservation.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("createReservation", "Exception lors de la réservation", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ReservationActivity,
                        "Une erreur est survenue : ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    }
