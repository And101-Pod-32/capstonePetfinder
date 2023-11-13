package com.example.capstonepetfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PetFinderAdapter (
    private val imageList:List<String>,
    private val nameList: List<String>,
    private val sexList: List<String>,
    private val breedList: List<String>

): RecyclerView.Adapter<PetFinderAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val petImage:ImageView
        val petName:TextView
        val petSex:TextView
        val petBreed: TextView

        init {
            petImage = view.findViewById(R.id.pet_image)
            petName = view.findViewById(R.id.name)
            petSex = view.findViewById(R.id.sex)
            petBreed = view.findViewById(R.id.breed)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item,parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(imageList[position])
            .centerCrop()
            .into(holder.petImage)

        holder.petBreed.text = breedList[position]
        holder.petName.text = nameList[position]
        holder.petSex.text = sexList[position]
    }

}



