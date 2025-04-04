package com.example.myapplication.exam_prepa._2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class ExamPrepaCustumAdapter : AppCompatActivity() {

    private lateinit var adapter : ExamPrepaEtudiantAdapter;
    private val etudiants = mutableListOf<Exam_Etudiant>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exam_prepa_custum_adapter)

        adapter = ExamPrepaEtudiantAdapter(this,R.layout.exam_etudiant_item,etudiants);
        val   listView = findViewById<ListView>(R.id.listViewEtudiants)
        listView.adapter  = adapter;

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val buttonAjouter = findViewById<Button>(R.id.buttonAjouter)

        buttonAjouter.setOnClickListener{
            val nom = editTextName.text.toString();
            val state  = when (radioGroup.checkedRadioButtonId) {
                R.id.radioPresent-> "Present"
                R.id.radioAbsent -> "Absent"
                else -> ""
            }
            if (nom.isEmpty() || state.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            etudiants.add(Exam_Etudiant(nom,state))
            adapter.notifyDataSetChanged()

            editTextName.text.clear()
            radioGroup.clearCheck()

        }

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                this,
                "Étudiant: ${etudiants[position].nom} - ${etudiants[position].state}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}