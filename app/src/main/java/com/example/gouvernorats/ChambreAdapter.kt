package com.example.gouvernorats

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ChambreAdapter(private var chambres: List<ChambrePackage>, private val onRoomSelected: (List<Long>) -> Unit) :
    RecyclerView.Adapter<ChambreAdapter.ChambreViewHolder>() {

    class ChambreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNbPersonne: TextView = itemView.findViewById(R.id.tvNbPersonne)
        val tvEspace: TextView = itemView.findViewById(R.id.tvEspace)
        val tvBed: TextView = itemView.findViewById(R.id.tvBed)
        val tvPrix: TextView = itemView.findViewById(R.id.tvPrix)
        val tvDisponibilite: TextView = itemView.findViewById(R.id.tvDisponibilite)
        val llEquipments: LinearLayout = itemView.findViewById(R.id.llEquipments)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChambreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chambre, parent, false)
        return ChambreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChambreViewHolder, position: Int) {
        val chambre = chambres[position]
        holder.tvNbPersonne.text = "Capacité : ${chambre.nbPersonne} personnes"
        holder.tvEspace.text = "${chambre.espace} m²"
        holder.tvBed.text = "Lit : ${chambre.bed}"
        holder.tvPrix.text = "${chambre.prix} TND"
        holder.tvDisponibilite.text =
            if (chambre.disponibilite) "Disponibilité : Disponible" else "Disponibilité : Réservée"
        holder.tvDisponibilite.setTextColor(
            if (chambre.disponibilite) Color.parseColor("#388E3C") else Color.parseColor("#D32F2F")
        )

        holder.llEquipments.removeAllViews() // Clean up before re-using
        val context = holder.itemView.context

        if (chambre.climatisation) addEquipmentIcon(context, holder.llEquipments, R.drawable.ic_ac, "Climatisation")
        if (chambre.balcon) addEquipmentIcon(context, holder.llEquipments, R.drawable.ic_balcony, "Balcon")
        if (chambre.televesion) addEquipmentIcon(context, holder.llEquipments, R.drawable.ic_tv, "Télévision")
        if (chambre.wifi) addEquipmentIcon(context, holder.llEquipments, R.drawable.ic_wifi, "Wi-Fi")
        if (chambre.salledebains) addEquipmentIcon(context, holder.llEquipments, R.drawable.ic_bathroom, "Salle de bains")

        // Change background color when clicked to select the room
        holder.itemView.setOnClickListener {
            chambre.isSelected = !chambre.isSelected
            holder.itemView.setBackgroundColor(
                if (chambre.isSelected) Color.LTGRAY else Color.WHITE
            )

            // Notify parent activity to update the selected rooms
            onRoomSelected(getSelectedRoomIds())
        }
    }

    private fun addEquipmentIcon(context: Context, container: LinearLayout, iconRes: Int, description: String) {
        val icon = ImageView(context)
        icon.setImageResource(iconRes)
        icon.layoutParams = LinearLayout.LayoutParams(64, 64).apply {
            marginEnd = 16
        }
        container.addView(icon)
    }

    override fun getItemCount(): Int = chambres.size

    fun updateData(newChambres: List<ChambrePackage>) {
        chambres = newChambres
        notifyDataSetChanged()
    }

    private fun getSelectedRoomIds(): List<Long> {
        return chambres.filter { it.isSelected }.map { it.id }
    }
}
