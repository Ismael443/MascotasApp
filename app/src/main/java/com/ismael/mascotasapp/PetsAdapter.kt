package com.ismael.mascotasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.ismael.mascotasapp.model.Pet

class PetsAdapter(private var pets: MutableList<Pet>) : RecyclerView.Adapter<PetsAdapter.PetViewHolder>() {


    fun updatePetList(petList: MutableList<Pet>) {
        this.pets = petList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_pet_item, parent, false)
        return PetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {

        val pet = pets[position]
        holder.namePet.text = pet.name
        holder.agePet.text = pet.age.toString()
        holder.breedPet.text = pet.breed

        Glide.with(holder.itemView.context)
            .load(pet.imageURL)
            .into(holder.petImage)


    }

    override fun getItemCount() = pets.size


    class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val namePet = itemView.findViewById<TextView>(R.id.nombre)
            val breedPet = itemView.findViewById<TextView>(R.id.raza)
            val agePet = itemView.findViewById<TextView>(R.id.edad)
            val petImage = itemView.findViewById<ImageView>(R.id.photo)
        }
    }

