package com.example.myapplication.tp3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException





class MainPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    private lateinit var btnSelectImage: Button
    private lateinit var editName: EditText
    private lateinit var radioGroupState: RadioGroup
    private lateinit var radioPresent: RadioButton
    private lateinit var radioAbsent: RadioButton

    private var selectedImagePath: String? = null

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisation des vues
        recyclerView = findViewById(R.id.recycler_view)
        btnSelectImage = findViewById(R.id.btn_select_image)
        editName = findViewById(R.id.edit_name)
        radioGroupState = findViewById(R.id.radio_group_state)
        radioPresent = findViewById(R.id.radio_present)
        radioAbsent = findViewById(R.id.radio_absent)

        // Configuration du RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(this, recyclerView, studentList)
        recyclerView.adapter = studentAdapter

        // Sélection d'une image
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Ajouter un étudiant
        findViewById<Button>(R.id.btn_add_student).setOnClickListener {
            addStudent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                selectedImagePath = saveImageLocally(it)
            }
        }
    }

    // Sauvegarde de l'image en local
    private fun saveImageLocally(imageUri: Uri): String? {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            val file = File(getExternalFilesDir(null), "student_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun addStudent() {
        val name = editName.text.toString().trim()
        val isPresent = radioPresent.isChecked

        if (name.isEmpty() || selectedImagePath == null) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        // Ajouter l'étudiant
        val student = Student(studentList.size + 1, name, isPresent, selectedImagePath!!)
        studentList.add(student)
        studentAdapter.notifyItemInserted(studentList.size - 1)

        // Réinitialiser les champs
        editName.text.clear()
        radioPresent.isChecked = true
        selectedImagePath = null
        Toast.makeText(this, "Étudiant ajouté avec succès", Toast.LENGTH_SHORT).show()
    }
}
