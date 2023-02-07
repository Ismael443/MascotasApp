package com.ismael.mascotasapp.model

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

object Database {
    const val COLLECTION_USERS = "usuarios"
    const val COLLECTION_PETS = "mascotas"


    suspend fun getPetsFromUser(email:String): List<Pet> {
        val snapshot = FirebaseFirestore.getInstance().collection(COLLECTION_USERS).document(email).collection(
            COLLECTION_PETS)
            .get()
            .await()
        val pets = mutableListOf<Pet>()
        for (documentSnapshot in snapshot){
            val pet = documentSnapshot.toObject(Pet::class.java)
            pets.add(pet)
        }
        return pets
    }

    fun getFlow(email:String): Flow<List<Pet>> {
        return FirebaseFirestore.getInstance()
            .collection(COLLECTION_USERS).document(email).collection(COLLECTION_PETS)
            .orderBy("nombre", Query.Direction.DESCENDING)
            .snapshots().map { snapshot ->
                snapshot.toObjects(Pet::class.java)
            }
    }
}
