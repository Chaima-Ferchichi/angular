package com.example.gouvernorats
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationPackage(
    val clientData: ClientData, // Informations du client
    val chambreIds: List<Long>, // Liste des ID de chambres sélectionnées
    val date_arrivee: String, // Date d'arrivée
    val date_depart: String, // Date de départ
    val adultes: Int, // Nombre d'adultes
    val enfants: Int // Nombre d'enfants
) : Parcelable

@Parcelize
data class ClientData(
    val nom: String, // Nom du client
    val email: String, // Email du client
    val telephone: String // Téléphone du client
) : Parcelable

