package com.example.myapplication.exam_prepa._2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class ExamPrepaListAdapter : AppCompatActivity() {
    private lateinit var editTextName: EditText;
    private lateinit var radioGroup: RadioGroup;
    private lateinit var addButton: Button;
    private lateinit var listViewAbsents: ListView;
    private lateinit var listViewPresents: ListView;
    private val absent = mutableListOf<String>()
    private val present = mutableListOf<String>();
    private lateinit var presentsAdapter:ArrayAdapter<String>
    private lateinit var absentsAdapter:ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_exam_prepa_list_adapter)
        editTextName = findViewById(R.id.editTextName)
        radioGroup = findViewById(R.id.radioGroup)
        addButton = findViewById(R.id.buttonAjouter)
        listViewAbsents = findViewById(R.id.listViewAbsents)
        listViewPresents = findViewById(R.id.listViewPresents)

        absentsAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,absent);
        presentsAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,present);

        listViewPresents.adapter = presentsAdapter;
        listViewAbsents.adapter = absentsAdapter;

        addButton.setOnClickListener{
            val nom = editTextName.text.toString();
            if(nom.isNotEmpty()){
                val seletedId = radioGroup.checkedRadioButtonId;
                if(seletedId == R.id.radioAbsent){
                   absent.add(nom);
                    absentsAdapter.notifyDataSetChanged()
                }else if(seletedId == R.id.radioPresent){
                    present.add(nom)
                    presentsAdapter.notifyDataSetChanged();
                }
                editTextName.text.clear()
                radioGroup.clearCheck()
            }
            else{
                Toast.makeText(this,"Veuillez entrer un nom", Toast.LENGTH_SHORT).show()
            }
        }

    }
}