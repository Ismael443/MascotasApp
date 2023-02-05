package com.ismael.mascotasapp.ui.homePets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ismael.mascotasapp.model.Pet

class HomePetVM : ViewModel() {

    private val petList = MutableLiveData<MutableList<Pet>>()
    private val db = FirebaseFirestore.getInstance()
    private val petsRef = db.collection("pets")

    fun getPetList(): LiveData<MutableList<Pet>> {
        return petList
    }

    fun loadPetList() {

        petsRef.get().addOnSuccessListener { result ->
            val petListResult = mutableListOf<Pet>()
            for (document in result) {
                val pet = document.toObject(Pet::class.java)
                pet.name = document["name"].toString()
                pet.breed = document["breed"].toString()
                pet.age = document["age"].toString().toInt()
                pet.imageURL = document["imageURL"].toString()
                petListResult.add(pet)
            }
            petList.postValue(petListResult)
        }
    }
}


