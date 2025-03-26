package com.example.myapplication.tp_

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.myapplication.R


class EtudiantAdapter(
    private val mContext: Context,
    private val resource: Int,
    private val etudiants: ArrayList<Etudiant>
) : ArrayAdapter<Etudiant>(mContext, resource, etudiants) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(mContext).inflate(resource, parent, false)

        val textNom = view.findViewById<TextView>(R.id.textNom)
        val textPresence = view.findViewById<TextView>(R.id.textPresence)
        val btnModifier = view.findViewById<Button>(R.id.btnModifier)
        val containerEtudiant = view.findViewById<View>(R.id.containerEtudiant)
        val btnDetails = view.findViewById<Button>(R.id.btnDetails)

        val etudiant = etudiants[position]

        textNom.text = etudiant.nom
        textPresence.text = if (etudiant.estPresent) "Présent" else "Absent" //

        btnDetails.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("NOM_ETUDIANT", etudiant.nom)
            intent.putExtra("EST_PRESENT", etudiant.estPresent)
            mContext.startActivity(intent)
        }

        if (etudiant.estPresent) {
            btnModifier.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark))
            containerEtudiant.setBackgroundResource(R.drawable.border_present)
            textPresence.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark)) // ✅ Texte en vert
        } else {
            btnModifier.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark))
            containerEtudiant.setBackgroundResource(R.drawable.border_absent)
            textPresence.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark)) // ✅ Texte en rouge
        }

        // Toggle de présence au clic
        btnModifier.setOnClickListener {
            etudiant.estPresent = !etudiant.estPresent
            notifyDataSetChanged()
        }

        return view
    }
}