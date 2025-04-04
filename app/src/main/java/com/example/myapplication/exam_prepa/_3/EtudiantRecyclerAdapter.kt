package com.example.myapplication.exam_prepa._3

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exam_prepa._2.Exam_Etudiant

class EtudiantRecyclerAdapter(
    private val context: Context,
    private var etudiants: MutableList<Exam_Etudiant>,
    private val onEtatChanged: (Int) -> Unit
) :RecyclerView.Adapter<EtudiantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exam_etudiant_item, parent, false)
        return EtudiantViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  etudiants.size
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        val etudiant = etudiants[position]

        holder.textNom.text = etudiant.nom
        holder.textPresence.text = etudiant.state


        when (etudiant.state.toLowerCase()) {
            "present" -> {
                holder.textPresence.setTextColor(ContextCompat.getColor(context, R.color.green))
                holder.itemView.setBackgroundResource(R.drawable.border_present)
            }
            else -> {
                holder.textPresence.setTextColor(ContextCompat.getColor(context, R.color.red))
                holder.itemView.setBackgroundResource(R.drawable.border_absent)
            }
        }


        holder.btnModifier.setOnClickListener {
            etudiants[position].state = if (etudiant.state.equals("Present", true)) "Absent" else "Present"
            notifyItemChanged(position)
            onEtatChanged(position)
        }


        holder.btnDetails.setOnClickListener {

        }
    }

    fun updateList(newList: List<Exam_Etudiant>) {
        etudiants = newList.toMutableList()
        notifyDataSetChanged()
    }


}