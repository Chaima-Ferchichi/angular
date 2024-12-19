import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.gouvernorats.R

class PaymentDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate le layout du Dialog
        val view = inflater.inflate(R.layout.activity_payment, container, false)

        // Configuration du bouton de paiement
        val submitPaymentButton = view.findViewById<Button>(R.id.btnSubmitPayment)
        submitPaymentButton.setOnClickListener {
            // Exemple de traitement du paiement
            Toast.makeText(requireContext(), "Paiement effectué avec succès !", Toast.LENGTH_SHORT).show()

            // Fermer le dialog après le paiement
            dismiss()
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent) // Fond transparent

            // Appliquer les dimensions après l'affichage
            dialog.setOnShowListener {
                val width = (resources.displayMetrics.widthPixels * 0.9).toInt() // Largeur 90%
                val height = ViewGroup.LayoutParams.WRAP_CONTENT // Hauteur ajustée
                setLayout(width, height) // Appliquer la taille
            }
        }

        return dialog
    }


    // Méthode pour afficher le DialogFragment depuis une activité ou un autre fragment
    fun showDialog(fragmentManager: androidx.fragment.app.FragmentManager) {
        show(fragmentManager, "PaymentDialog")
    }
}
