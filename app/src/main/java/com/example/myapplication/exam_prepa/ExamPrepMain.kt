package com.example.myapplication.exam_prepa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.tp1.Calculatrice

class ExamPrepMain : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_exam_prep_main)
        val editeTextNom = findViewById<EditText>(R.id.textNom);
        val editTextPrenom = findViewById<EditText>(R.id.textPrenom);
        val editTextAge = findViewById<EditText>(R.id.textAge);
        val editTextEmail = findViewById<EditText>(R.id.textEmail);
        val editTextMotDePasse = findViewById<EditText>(R.id.textPassword);
        val editetextConfermePassword = findViewById<EditText>(R.id.textConfermePassword);
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val buttonInscription = findViewById<Button>(R.id.buttonInscription);
        val textMessage = findViewById<TextView>(R.id.textMessage);



        buttonInscription.setOnClickListener {
           var nom = editeTextNom.text.toString();
           var prenom = editTextPrenom.text.toString();
           var age = editTextAge.text.toString();
           var email = editTextEmail.text.toString();
           var password = editTextMotDePasse.text.toString();
           var confirmPassword = editetextConfermePassword.text.toString();
           var selectedGenreId = radioGroupGender.checkedRadioButtonId;
           val selected  = findViewById<RadioButton>(selectedGenreId)?.text.toString();

           if(nom.isEmpty() || prenom.isEmpty() || password.isEmpty() || email.isEmpty() ||
               confirmPassword.isEmpty() || age.isEmpty() || selectedGenreId == -1){
               textMessage.visibility = View.VISIBLE
               textMessage.setText("Veuillez remplir tous les champs.")
           }else if(confirmPassword != password){
               textMessage.visibility = View.VISIBLE
               textMessage.setText("Les mots de passe ne correspondent pas.")
           }else {
               textMessage.visibility = View.GONE
               showAlert("Inscription réussie pour $prenom $nom")
               val intent= Intent(this, ExamPrepaCalculatrice::class.java)
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