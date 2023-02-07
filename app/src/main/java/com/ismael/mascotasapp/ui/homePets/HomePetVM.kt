@file:Suppress("UNCHECKED_CAST")

package com.ismael.mascotasapp.ui.homePets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.ismael.mascotasapp.model.Pet

class HomePetVM(currentUser: String) : ViewModel() {

    private val petList = MutableLiveData<MutableList<Pet>>()
    private val db = FirebaseFirestore.getInstance()
    private val petsRef = db.collection("usuarios")
        .document(currentUser).collection("mascotas").get()

    fun getPetList(): LiveData<MutableList<Pet>> {
        return petList
    }

    fun loadPetList() {

        petsRef.addOnSuccessListener { result ->
            val petListResult = mutableListOf<Pet>()
            for (document in result) {
                val pet = document.toObject(Pet::class.java)
                pet.name = document["nombre"].toString()
                pet.breed = document["raza"].toString()
                pet.age = document["edad"].toString().toInt()
                pet.imageURL = document["imagenURL"].toString()
                petListResult.add(pet)
            }
            petList.postValue(petListResult)
        }
    }
}

class HomePetVMFactory (private val email: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomePetVM(email) as T
    }
}


