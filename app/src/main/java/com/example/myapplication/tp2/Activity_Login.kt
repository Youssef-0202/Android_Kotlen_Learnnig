package com.example.myapplication.tp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class Activity_Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        var editTextEmail = findViewById<EditText>(R.id.editTextEmail);
        var editTextPassword = findViewById<EditText>(R.id.editTextPassword);
        var buttonLogin = findViewById<Button>(R.id.buttonLogin);

        buttonLogin.setOnClickListener{
            val email = editTextEmail.text.toString();
            val passord = editTextPassword.text.toString();

            if (email.isEmpty() || passord.isEmpty()) {
                showAlert("Insert all value !")
            }else if(email != "yussef@gmail.com" && passord != "00001111"  ){
                showAlert("Invalid user !")
            } else{
              var intent = Intent(this,Activity_List_Absent::class.java)
                intent.putExtra("email",email)
                startActivity(intent)
            }

        }
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Succès")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


}