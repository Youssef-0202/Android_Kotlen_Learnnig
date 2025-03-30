package com.example.myapplication.devoir

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.tp3.DetailsActivity
import java.io.File


class StudentAdapter(
    private val context: Context,
    private val studentList: MutableList<Student>
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    // Interface for handling external events
    interface OnStudentChangeListener {
        fun onStudentUpdated(position: Int, student: Student)
    }

    private var studentChangeListener: OnStudentChangeListener? = null

    fun setOnStudentChangeListener(listener: OnStudentChangeListener) {
        this.studentChangeListener = listener
    }

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentImage: ImageView = view.findViewById(R.id.imageEtudiant)
        val studentName: TextView = view.findViewById(R.id.textNom)
        val studentStatus: TextView = view.findViewById(R.id.textPresence)
        val btnModifier: Button = view.findViewById(R.id.btnModifier)
        val btnDetails: Button = view.findViewById(R.id.btnDetails)
        val itemContainer: View = view.findViewById(R.id.containerEtudiant) // Add this to your item_student.xml

        fun bind(student: Student, position: Int) {
            studentName.text = student.name
            updatePresenceUI(student.isPresent)

            // Load image
            try {
                if(student.imagePath != null){
                    if (student.imagePath.startsWith("content://")) {
                        studentImage.setImageURI(Uri.parse(student.imagePath))
                    } else {
                        val bitmap = BitmapFactory.decodeFile(student.imagePath)
                        if (bitmap != null) {
                            studentImage.setImageBitmap(bitmap)
                        } else {
                            studentImage.setImageResource(R.drawable.user_logo)
                        }
                    }
                }
            } catch (e: Exception) {
                studentImage.setImageResource(R.drawable.user_logo)
            }

            // Modifier button click
            btnModifier.setOnClickListener {
                student.isPresent = !student.isPresent
                updatePresenceUI(student.isPresent)
                studentChangeListener?.onStudentUpdated(position, student)
            }

            // Details button click
            btnDetails.setOnClickListener {
                val intent = Intent(context, DetailsDevoirActivity::class.java).apply {
                    putExtra("id", student.id)
                    putExtra("nom", student.name)
                    putExtra("estPresent", student.isPresent)
                    putExtra("imagePath", student.imagePath)
                }
                (context as Activity).startActivityForResult(intent, 1)
            }
        }

        private fun updatePresenceUI(isPresent: Boolean) {
            if (isPresent) {
                itemContainer.background = ContextCompat.getDrawable(context, R.drawable.border_present)
                studentStatus.text = "Présent"
                studentStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
                btnModifier.text = "Marquer Absent"
            } else {
                itemContainer.background = ContextCompat.getDrawable(context, R.drawable.border_absent)
                studentStatus.text = "Absent"
                studentStatus.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                btnModifier.text = "Marquer Présent"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], position)
    }

    override fun getItemCount(): Int = studentList.size
}