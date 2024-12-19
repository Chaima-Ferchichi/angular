package com.example.gouvernorats

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HotelPackage(
    val id: Long,
    val nom: String,
    val localisation: String,
    val telephone: String,
    val fax: String?,
    val etoile: String?,
    val email: String?,
    val type: String?,
    val description: String?,
    val entertaiment: Boolean,
    val piscines: Boolean,
    val parking: Boolean,
    val restauration: Boolean,
    val serviceChambre: Boolean,
    val wifi: Boolean,
    val etat: String?,
    val prix: Double,
    val dispo: Boolean,
    var images: List<String> = emptyList()
) : Parcelable
