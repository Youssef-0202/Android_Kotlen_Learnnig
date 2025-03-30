package com.example.myapplication.tp3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

class HomeActivity : AppCompatActivity() {

    private lateinit var editTextNom: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnAjouter: Button
    private lateinit var listViewEtudiants: ListView
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private val etudiantsList = ArrayList<Etudiant>()
    private lateinit var etudiantAdapter: EtudiantAdapter
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var currentStudent: Etudiant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        editTextNom = findViewById(R.id.idNomEtudiant)
        radioGroup = findViewById(R.id.idRadioGroup)
        btnAjouter = findViewById(R.id.idAjouter)


        listViewEtudiants = findViewById(R.id.listViewEtudiants)
        etudiantAdapter = EtudiantAdapter(this, etudiantsList)
        listViewEtudiants.adapter = etudiantAdapter

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                if (imageUri != null) {
                    val imageView = findViewById<ImageView>(R.id.imageEtudiant)
                    imageView.setImageURI(imageUri)
                    saveImageToInternalStorage(imageUri)
                }
            }
        }

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

        val btnSelectImage = findViewById<ImageButton>(R.id.idSelectImage)
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                val savedImagePath = saveImageToInternalStorage(it)
                currentStudent.imagePath = savedImagePath
                etudiantAdapter.notifyDataSetChanged()
            }
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val nom = data.getStringExtra("nom")
            val estPresent = data.getBooleanExtra("estPresent", false)

            val etudiant = etudiantsList.find { it.nom == nom }
            if (etudiant != null) {
                etudiant.estPresent = estPresent
                etudiantAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun saveImageToInternalStorage(imageUri: Uri): String {
        val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
        val fileName = "etudiant_${UUID.randomUUID()}.jpg"
        val directory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Etudiants")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, fileName)
        val outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return file.absolutePath
    }

}