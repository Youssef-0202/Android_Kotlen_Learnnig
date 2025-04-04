package com.example.myapplication.exam_prepa._3

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class EtudiantViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageEtudiant)
    val textNom: TextView = itemView.findViewById(R.id.textNom)
    val textPresence: TextView = itemView.findViewById(R.id.textPresence)
    val btnDetails: Button = itemView.findViewById(R.id.btnDetails)
    val btnModifier: Button = itemView.findViewById(R.id.btnModifier)
}