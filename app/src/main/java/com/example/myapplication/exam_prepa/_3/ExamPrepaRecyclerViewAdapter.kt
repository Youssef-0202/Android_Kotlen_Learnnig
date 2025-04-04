package com.example.myapplication.exam_prepa._3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exam_prepa._2.Exam_Etudiant

class ExamPrepaRecyclerViewAdapter : AppCompatActivity() {

    private lateinit var adapter: EtudiantRecyclerAdapter
    private val etudiantsList = mutableListOf<Exam_Etudiant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exam_prepa_recycler_view_adapter)

        val recyclerView = findViewById<RecyclerView>(R.id.listViewEtudiants)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EtudiantRecyclerAdapter(
            this,
            etudiantsList,
            onEtatChanged = { position ->
                Toast.makeText(this, "État modifié pour ${etudiantsList[position].nom}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter

        val buttonAjouter = findViewById<Button>(R.id.buttonAjouter)
        buttonAjouter.setOnClickListener {
            val nom = findViewById<EditText>(R.id.editTextName).text.toString()
            val state = when (findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId) {
                R.id.radioPresent -> "Present"
                else -> "Absent"
            }

            if (nom.isNotEmpty()) {
                etudiantsList.add(Exam_Etudiant(nom, state))
                adapter.notifyItemInserted(etudiantsList.size - 1)

                findViewById<EditText>(R.id.editTextName).text.clear()
                findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
            }
        }


    }
}