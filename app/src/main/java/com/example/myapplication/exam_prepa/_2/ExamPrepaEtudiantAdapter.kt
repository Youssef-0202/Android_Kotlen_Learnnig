package com.example.myapplication.exam_prepa._2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myapplication.R

class ExamPrepaEtudiantAdapter(
    context:Context,
    ressource:Int,
    private val etudiants :MutableList<Exam_Etudiant>
) :ArrayAdapter<Exam_Etudiant>(context,ressource,etudiants) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.exam_etudiant_item,
            parent,
            false
        )

        val etudiant = etudiants[position]

        val imageView = view.findViewById<ImageView>(R.id.imageEtudiant)
        val textNom = view.findViewById<TextView>(R.id.textNom)
        val textPresence = view.findViewById<TextView>(R.id.textPresence)

        textNom.text = etudiant.nom
        textPresence.text = etudiant.state

        updateAppearance(view, textPresence, etudiant.state)


        view.findViewById<Button>(R.id.btnDetails).setOnClickListener {

        }

        view.findViewById<Button>(R.id.btnModifier).setOnClickListener {
            etudiant.state = if (etudiant.state.equals("Present", ignoreCase = true)) {
                "Absent"
            } else {
                "Present"
            }

            textPresence.text = etudiant.state
            updateAppearance(view, textPresence, etudiant.state)

            notifyDataSetChanged()
        }

        return  view;
    }

    private fun updateAppearance(view: View, textPresence: TextView, state: String) {
        when (state.toLowerCase()) {
            "present" -> {
                textPresence.setTextColor(ContextCompat.getColor(context, R.color.green))
                view.setBackgroundResource(R.drawable.border_present)
                textPresence.text = "Présent"
            }
            else -> {
                textPresence.setTextColor(ContextCompat.getColor(context, R.color.red))
                view.setBackgroundResource(R.drawable.border_absent)
                textPresence.text = "Absent"
            }
        }
    }
}