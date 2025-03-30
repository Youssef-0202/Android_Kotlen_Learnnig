package com.example.myapplication.tp3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class DetailsActivity : AppCompatActivity() {

    private lateinit var imageEtudiant: ImageView
    private lateinit var textNom: TextView
    private lateinit var textPresence: TextView
    private lateinit var btnModifier: Button
    private lateinit var btnRetour: Button

    private var estPresent: Boolean = false
    private lateinit var nomEtudiant: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Récupérer les éléments UI
        imageEtudiant = findViewById(R.id.imageEtudiant)
        textNom = findViewById(R.id.textNom)
        textPresence = findViewById(R.id.textPresence)
        btnModifier = findViewById(R.id.btnModifier)
        btnRetour = findViewById(R.id.btnRetour)

        // Récupérer les données passées via Intent
        nomEtudiant = intent.getStringExtra("nom") ?: "Inconnu"
        estPresent = intent.getBooleanExtra("estPresent", false)

        // Mettre à jour l'interface utilisateur
        textNom.text = nomEtudiant
        updatePresenceUI()

        // Gérer le clic sur Modifier
        btnModifier.setOnClickListener {
            estPresent = !estPresent
            updatePresenceUI()
        }

        // Gérer le retour vers HomeActivity
        btnRetour.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("nom", nomEtudiant)
            resultIntent.putExtra("estPresent", estPresent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun updatePresenceUI() {
        if (estPresent) {
            textPresence.text = "Présent"
            textPresence.setTextColor(getColor(android.R.color.holo_green_dark))
            btnModifier.text = "Marquer Absent"
        } else {
            textPresence.text = "Absent"
            textPresence.setTextColor(getColor(android.R.color.holo_red_dark))
            btnModifier.text = "Marquer Présent"
        }
    }
}