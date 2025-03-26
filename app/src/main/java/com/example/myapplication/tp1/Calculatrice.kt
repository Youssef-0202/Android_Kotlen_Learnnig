package com.example.myapplication.tp1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class Calculatrice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculatrice)

        val nom = intent.getStringExtra("nom")


        if (nom != null) {

            val textViewNom = findViewById<TextView>(R.id.textViewNom)
            textViewNom.visibility = View.VISIBLE// Remplacez par l'ID de votre TextView
            textViewNom.text = "Bonjour, $nom"
        }

        val editTextA = findViewById<EditText>(R.id.editTextA);
        val editTextB = findViewById<EditText>(R.id.editTextB);
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

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
