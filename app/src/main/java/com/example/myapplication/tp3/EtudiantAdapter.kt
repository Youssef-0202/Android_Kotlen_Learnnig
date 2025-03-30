package com.example.myapplication.tp3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import java.io.File

class EtudiantAdapter(
    private val mContext: Context,
    private val etudiants: ArrayList<Etudiant>
) : ArrayAdapter<Etudiant>(mContext, R.layout.item_student, etudiants) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(mContext).inflate(R.layout.item_student, parent, false)

        // Bind UI elements
        val imageEtudiant = view.findViewById<ImageView>(R.id.imageEtudiant)
        val textNom = view.findViewById<TextView>(R.id.textNom)
        val textPresence = view.findViewById<TextView>(R.id.textPresence)
        val btnModifier = view.findViewById<Button>(R.id.btnModifier)
        val btnDetails = view.findViewById<Button>(R.id.btnDetails)
        val containerEtudiant = view.findViewById<LinearLayout>(R.id.containerEtudiant)

        // Get student data
        val etudiant = etudiants[position]

        // Set student name
        textNom.text = etudiant.nom

        // Update presence status dynamically
        if (etudiant.estPresent) {
            textPresence.text = "Présent"
            textPresence.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark))

            btnModifier.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark))
            containerEtudiant.setBackgroundResource(R.drawable.border_present)
        } else {
            textPresence.text = "Absent"
            textPresence.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_red_dark))

            btnModifier.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark))
            containerEtudiant.setBackgroundResource(R.drawable.border_absent)
        }

        if (etudiant.imagePath != null) {
            val imgUri = Uri.fromFile(File(etudiant.imagePath))
            imageEtudiant.setImageURI(imgUri)
        } else {
            imageEtudiant.setImageResource(R.drawable.user_logo)
        }

        // Handle "Modifier" button click (Toggle Presence)
        btnModifier.setOnClickListener {
            etudiant.estPresent = !etudiant.estPresent
            notifyDataSetChanged() // Refresh UI
        }

        btnDetails.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("nom", etudiant.nom)
            intent.putExtra("estPresent", etudiant.estPresent)
            (mContext as Activity).startActivityForResult(intent, 1)
        }



        return view
    }
}
