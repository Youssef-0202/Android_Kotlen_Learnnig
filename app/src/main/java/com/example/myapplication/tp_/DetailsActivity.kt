package com.example.myapplication.tp_

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        val textViewDetails = findViewById<TextView>(R.id.textViewDetails)

        val nom = intent.getStringExtra("NOM_ETUDIANT")
        val statut = intent.getBooleanExtra("EST_PRESENT", false)

        textViewDetails.text = "Nom: $nom\nStatut: ${if (statut) "Présent" else "Absent"}"

    }
}