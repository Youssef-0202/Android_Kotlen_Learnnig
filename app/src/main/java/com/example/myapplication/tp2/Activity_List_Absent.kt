package com.example.myapplication.tp2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class Activity_List_Absent : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var buttonAjouter: Button
    private lateinit var listViewAbsents: ListView
    private lateinit var listViewPresents: ListView
    private val absents = mutableListOf<String>()
    private val presents = mutableListOf<String>()
    private lateinit var absentsAdapter: ArrayAdapter<String>
    private lateinit var presentsAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_absent)

        editTextName = findViewById(R.id.editTextName)
        radioGroup = findViewById(R.id.radioGroup)
        buttonAjouter = findViewById(R.id.buttonAjouter)
        listViewAbsents = findViewById(R.id.listViewAbsents)
        listViewPresents = findViewById(R.id.listViewPresents)

        absentsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, absents)
        presentsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, presents)
        listViewAbsents.adapter = absentsAdapter
        listViewPresents.adapter = presentsAdapter

        buttonAjouter.setOnClickListener {
            val nom = editTextName.text.toString().trim()
            if (nom.isNotEmpty()) {
                val selectedId = radioGroup.checkedRadioButtonId
                if (selectedId == R.id.radioAbsent) {
                    absents.add(nom)
                    absentsAdapter.notifyDataSetChanged()
                } else if (selectedId == R.id.radioPresent) {
                    presents.add(nom)
                    presentsAdapter.notifyDataSetChanged()
                }
                editTextName.text.clear()
                radioGroup.clearCheck()
            } else {
                Toast.makeText(this, "Veuillez entrer un nom", Toast.LENGTH_SHORT).show()
            }
        }


    }
}