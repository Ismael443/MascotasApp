package com.ismael.mascotasapp.ui.createPet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.ismael.mascotasapp.R
import com.ismael.mascotasapp.model.Pet


class CreatePetFragment : Fragment() {

    private lateinit var imagePet: ImageView
    private lateinit var namePet: EditText
    private lateinit var agePet: EditText
    private lateinit var breedPet: EditText
    private lateinit var btnAddPet: Button
    private lateinit var btnEditImage: Button
    private lateinit var db: FirebaseFirestore



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_pet, container, false)


        imagePet = view.findViewById(R.id.photo)
        namePet = view.findViewById(R.id.addPetName)
        agePet = view.findViewById(R.id.addPetAge)
        breedPet = view.findViewById(R.id.addPetRaza)
        btnAddPet = view.findViewById(R.id.btnAddPet)
        btnEditImage = view.findViewById(R.id.btn_photo)

        btnAddPet.setOnClickListener {
            val name = namePet.text.toString()
            val breed = breedPet.text.toString()
            val age = agePet.text.toString().toInt()
            val imageURL = imagePet.toString()
            val pet = Pet(name, breed,age, imageURL)

            db.collection("pets")
                .add(pet)
                .addOnSuccessListener {
                    Toast.makeText(
                        activity,
                        "MASCOTA REGISTRADA CON ÉXITO",
                        Toast.LENGTH_SHORT
                    ).show()

                    //SI SE REGISTRA CORRECTAMENTE, LIMPIAMOS LOS CAMPOS Y ESTABLECEMOS UNA IMAGEN
                    //POR DEFECTO
                    namePet.text.clear()
                    agePet.text.clear()
                    breedPet.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(activity, "ERROR AL REGISTRAR LA MASCOTA", Toast.LENGTH_SHORT).show()
                }
        }
        return view
    }
}
