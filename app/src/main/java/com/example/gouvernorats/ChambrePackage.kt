package com.example.gouvernorats
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChambrePackage(
    val id: Long,
    val nbPersonne: Int,
    val espace: Double,
    val bed: String,
    val disponibilite: Boolean,
    val climatisation: Boolean,
    val balcon: Boolean,
    val televesion: Boolean,
    val wifi: Boolean,
    val salledebains: Boolean,
    val prix: Double,
    val reservationId: Int? ,// Peut être null
    var isSelected: Boolean = false // Added property to track selection state
) : Parcelable

