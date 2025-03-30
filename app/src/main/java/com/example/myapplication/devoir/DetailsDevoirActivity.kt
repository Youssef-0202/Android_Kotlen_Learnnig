package com.example.myapplication.devoir

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class DetailsDevoirActivity : AppCompatActivity() {
    private lateinit var imageEtudiant: ImageView
    private lateinit var textNom: TextView
    private lateinit var textPresence: TextView
    private lateinit var btnModifier: Button
    private lateinit var btnRetour: Button

    private var estPresent: Boolean = false
    private lateinit var nomEtudiant: String
    private var idEtudiant: Int = -1
    private lateinit var imagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_devoir)

        val container = findViewById<View>(R.id.container)

        // Initialize UI elements
        imageEtudiant = findViewById(R.id.imageEtudiant)
        textNom = findViewById(R.id.textNom)
        textPresence = findViewById(R.id.textPresence)
        btnModifier = findViewById(R.id.btnModifier)
        btnRetour = findViewById(R.id.btnRetour)

        // Get data from intent
        idEtudiant = intent.getIntExtra("id", -1)
        nomEtudiant = intent.getStringExtra("nom") ?: "Inconnu"
        estPresent = intent.getBooleanExtra("estPresent", false)
        imagePath = intent.getStringExtra("imagePath") ?: ""

        // Update UI
        textNom.text = nomEtudiant
        updatePresenceUI()
        loadStudentImage()

        btnModifier.setOnClickListener {
            estPresent = !estPresent
            updatePresenceUI()
        }

        if (estPresent) {
            container.background = ContextCompat.getDrawable(this, R.drawable.border_present)
        } else {
            container.background = ContextCompat.getDrawable(this, R.drawable.border_absent)
        }

        btnRetour.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("id", idEtudiant)
                putExtra("estPresent", estPresent)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun loadStudentImage() {
        try {
            if (imagePath.startsWith("content://")) {
                imageEtudiant.setImageURI(Uri.parse(imagePath))
            } else {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                if (bitmap != null) {
                    imageEtudiant.setImageBitmap(bitmap)
                } else {
                    imageEtudiant.setImageResource(R.drawable.user_logo)
                }
            }
        } catch (e: Exception) {
            imageEtudiant.setImageResource(R.drawable.user_logo)
        }
    }

    private fun updatePresenceUI() {
        if (estPresent) {
            textPresence.text = "Présent"
            textPresence.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
            btnModifier.text = "Marquer Absent"
        } else {
            textPresence.text = "Absent"
            textPresence.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            btnModifier.text = "Marquer Présent"
        }
    }
}