package com.example.myapplication.tp_

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class HomeActivity : AppCompatActivity() {

    private lateinit var editTextNom: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnAjouter: Button
    private lateinit var listViewEtudiants: ListView

    private val etudiantsList = ArrayList<Etudiant>()
    private lateinit var etudiantAdapter: EtudiantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        editTextNom = findViewById(R.id.idNomEtudiant)
        radioGroup = findViewById(R.id.idRadioGroup)
        btnAjouter = findViewById(R.id.idAjouter)
        listViewEtudiants = findViewById(R.id.listViewEtudiants)

        etudiantAdapter = EtudiantAdapter(this, R.layout.item_etudiant, etudiantsList)
        listViewEtudiants.adapter = etudiantAdapter

        btnAjouter.setOnClickListener {
            val nomEtudiant = editTextNom.text.toString().trim()
            if (nomEtudiant.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un nom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Veuillez sélectionner Absent ou Présent", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val estPresent = selectedId == R.id.idPresent
            etudiantsList.add(Etudiant(nomEtudiant, estPresent))
            etudiantAdapter.notifyDataSetChanged()

            editTextNom.text.clear()
            radioGroup.clearCheck()
        }
    }
}