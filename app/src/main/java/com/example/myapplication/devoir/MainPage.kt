package com.example.myapplication.devoir

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
        studentAdapter = StudentAdapter(this, studentList)  // Removed recyclerView parameter
        recyclerView.adapter = studentAdapter

        // Sélection d'une image
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }

        // Initialize RecyclerView and adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(this, studentList)
        recyclerView.adapter = studentAdapter

        // Set item click listener
        // Set up the listener for student changes
        studentAdapter.setOnStudentChangeListener(object : StudentAdapter.OnStudentChangeListener {
            override fun onStudentUpdated(position: Int, student: Student) {
                // Update the student in the list
                studentList[position] = student
                // You can add additional logic here if needed
            }
        })

        // Ajouter un étudiant
        findViewById<Button>(R.id.btn_add_student).setOnClickListener {
            addStudent()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra("id", -1) ?: -1
            val isPresent = data?.getBooleanExtra("estPresent", false) ?: false

            // Find and update the student
            val index = studentList.indexOfFirst { it.id == id }
            if (index != -1) {
                studentList[index].isPresent = isPresent
                studentAdapter.notifyItemChanged(index)
            }
        }

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                // Autoriser l'accès à l'image même après fermeture de l'application
                contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)

                // Stocker l'URI au lieu d'un chemin absolu
                selectedImagePath = it.toString()
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra("id", -1) ?: -1
            val isPresent = data?.getBooleanExtra("estPresent", false) ?: false

            // Update the student's presence status
            studentList.find { it.id == id }?.isPresent = isPresent
            studentAdapter.notifyDataSetChanged()
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
        try {
            val name = editName.text.toString().trim()
            val isPresent = radioPresent.isChecked

            if (name.isEmpty() || selectedImagePath == null) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return
            }

            Log.d("MainPage", "Selected image path: $selectedImagePath")

            // Save the image locally and get the path
            val imageUri = Uri.parse(selectedImagePath!!)
            val savedImagePath = saveImageLocally(imageUri) ?: selectedImagePath!!

            Log.d("MainPage", "Saved image path: $savedImagePath")

            // Add the student
            val student = Student(studentList.size + 1, name, isPresent, savedImagePath)
            studentList.add(student)
            studentAdapter.notifyItemInserted(studentList.size - 1)

            // Reset fields
            editName.text.clear()
            radioPresent.isChecked = true
            selectedImagePath = null
            Toast.makeText(this, "Étudiant ajouté avec succès", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("MainPage", "Error adding student", e)
            Toast.makeText(this, "Erreur: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }
}
