package com.example.myapplication.tp3

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.io.File




/*class StudentAdapter(context: Context, private val students: MutableList<Student>) :
    ArrayAdapter<Student>(context, 0, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)

        // Récupérer les éléments UI
        val student = students[position]
        val imgStudent = view.findViewById<ImageView>(R.id.student_image)
        val txtName = view.findViewById<TextView>(R.id.student_name)
        val txtStatus = view.findViewById<TextView>(R.id.student_status)
        val btnDetails = view.findViewById<Button>(R.id.btn_details)
        val btnToggleStatus = view.findViewById<Button>(R.id.btn_toggle_status)

        // Remplir les données
        txtName.text = student.name
        txtStatus.text = if (student.isPresent) "Présent" else "Absent"
        txtStatus.setTextColor(if (student.isPresent) context.getColor(R.color.green) else context.getColor(R.color.red))

        // Charger l’image avec Glide
        Glide.with(context).load(student.imagePath).into(imgStudent)

        // Changer le statut en cliquant sur le bouton
        btnToggleStatus.text = if (student.isPresent) "Absent" else "Présent"
        btnToggleStatus.setOnClickListener {
            student.isPresent = !student.isPresent
            notifyDataSetChanged()  // Mettre à jour la ListView
        }

        return view
    }
}
*/

class StudentAdapter(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val studentList: MutableList<Student>
) :RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val studentImage: ImageView = view.findViewById(R.id.student_image)
        val studentName: TextView = view.findViewById(R.id.student_name)
        val studentStatus: TextView = view.findViewById(R.id.student_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }


    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]

        holder.studentName.text = student.name
        holder.studentStatus.text = if (student.isPresent) "Présent" else "Absent"

        // Charger l’image depuis le stockage local
        val imgFile = File(student.imagePath)
        if (imgFile.exists()) {
            val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            holder.studentImage.setImageBitmap(bitmap)
        } else {
            holder.studentImage.setImageResource(R.drawable.user_logo) // Image par défaut
        }
    }

    override fun getItemCount(): Int = studentList.size

}
