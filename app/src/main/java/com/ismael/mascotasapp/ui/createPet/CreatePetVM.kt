package com.ismael.mascotasapp.ui.createPet

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ismael.mascotasapp.model.Pet

class CreatePetVM : ViewModel(){

    private lateinit var db : FirebaseFirestore

    fun addPetToUser(pet : Pet, email: String, activity: AppCompatActivity ){
        db = FirebaseFirestore.getInstance()

        val petMap = hashMapOf(
            "nombre" to pet.name,
            "edad" to pet.age,
            "raza" to pet.breed,
            "imagenURL" to pet.imageURL
        )

        db.collection("usuarios").document(email).
        collection("mascotas").document(pet.name).set(petMap)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(activity, "SU MASCOTA SE HA REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity, "ERROR AL REGISTRAR LA MASCOTA", Toast.LENGTH_SHORT).show()
                }
            }

    }
}