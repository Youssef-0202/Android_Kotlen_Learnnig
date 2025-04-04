package com.example.myapplication.exam_prepa._1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class ExamPrepaCalculatrice : AppCompatActivity() {

    val nom = intent.getStringExtra("nom")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exam_prepa_calculatrice)

        val editTextA = findViewById<EditText>(R.id.editTextA);
        val editTextB = findViewById<EditText>(R.id.editTextB);
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        if (nom != null) {
            val textViewNom = findViewById<TextView>(R.id.textViewNom)
            textViewNom.visibility = View.VISIBLE
            textViewNom.text = "Bonjour, $nom"
        }

        var selectedOperation = ""

        buttonAdd.setOnClickListener{selectedOperation = "+"}
        buttonSubtract.setOnClickListener{selectedOperation = "-"}
        buttonMultiply.setOnClickListener{selectedOperation = "*"}
        buttonDivide.setOnClickListener{selectedOperation = "/"}

        buttonCalculate.setOnClickListener {
            val a = editTextA.text.toString().toDoubleOrNull()
            val b = editTextB.text.toString().toDoubleOrNull()

            if (a == null || b == null) {
                Toast.makeText(this, "Veuillez entrer des " +
                        "valeurs valides pour a et b", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = when (selectedOperation) {
                "+" -> a + b
                "-" -> a - b
                "*" -> a * b
                "/" -> if (b != 0.0) a / b else Double.NaN
                else -> Double.NaN
            }

            if (result.isNaN()) {
                textViewResult.text = "Erreur: Division par zéro"
            } else {
                textViewResult.text = "Résultat: $result"
            }

        }


    }
}