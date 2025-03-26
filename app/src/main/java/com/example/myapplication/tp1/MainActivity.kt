package com.example.myapplication.tp1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class MainActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextNom = findViewById<EditText>(R.id.editTextNom);
        val editTextPrenom = findViewById<EditText>(R.id.editTextPrenom);
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail);
        val editTextMotDePasse = findViewById<EditText>(R.id.editTextMotDePasse);
        val editTextMotDePasseValidation = findViewById<EditText>(R.id.editTextMotDePasseValidation);
        val editTextAge = findViewById<EditText>(R.id.editTextAge)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val buttonInscription = findViewById<Button>(R.id.buttonInscription)
        val errorField = findViewById<EditText>(R.id.errorField)

        buttonInscription.setOnClickListener{
            var nom = editTextNom.text.toString()
            var prenom = editTextPrenom.text.toString()
            var email = editTextEmail.text.toString()
            var password = editTextMotDePasse.text.toString()
            var confirmPassword = editTextMotDePasseValidation.text.toString()
            var age = editTextAge.text.toString()
            var selectedGenreId = radioGroupGender.checkedRadioButtonId;
            val selectedGenre = findViewById<RadioButton>(selectedGenreId)?.text.toString()

            if(nom.isEmpty() || prenom.isEmpty() || password.isEmpty() || email.isEmpty() ||
               confirmPassword.isEmpty() || age.isEmpty() || selectedGenreId == -1){
                errorField.visibility = View.VISIBLE
                errorField.setText("Veuillez remplir tous les champs.")
            }else if(confirmPassword != password){
                errorField.visibility = View.VISIBLE
                errorField.setText("Les mots de passe ne correspondent pas.")
            }else {
                errorField.visibility = View.GONE
                showAlert("Inscription réussie pour $prenom $nom")
                val intent=Intent(this, Calculatrice::class.java)
                intent.putExtra("nom",nom);
                startActivity(intent)
            }

        }


    }

    private fun showAlert(message:String){
        AlertDialog.Builder(this)
            .setTitle("Succès")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }



}


