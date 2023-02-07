package com.ismael.mascotasapp.ui.createPet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.ismael.mascotasapp.R
import com.ismael.mascotasapp.model.Pet
import java.util.*


@Suppress("DEPRECATION")
class CreatePetFragment : Fragment() {

    private lateinit var imagePet: ImageView
    private lateinit var namePet: EditText
    private lateinit var agePet: EditText
    private lateinit var breedPet: EditText
    private lateinit var btnAddPet: Button
    private lateinit var btnEditImage: Button
    private lateinit var btnDeleteImage : Button
    private lateinit var db: FirebaseFirestore
    private val viewModel by viewModels<CreatePetVM>()
    private lateinit var activity: AppCompatActivity

    private val OK_GALLERY = 26
    private var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_pet, container, false)

        activity = requireActivity() as AppCompatActivity
        val currentUser = activity.intent.extras?.getString("email")

        db = FirebaseFirestore.getInstance()

        imagePet = view.findViewById(R.id.photo)
        namePet = view.findViewById(R.id.addPetName)
        agePet = view.findViewById(R.id.addPetAge)
        breedPet = view.findViewById(R.id.addPetRaza)
        btnAddPet = view.findViewById(R.id.btnAddPet)
        btnEditImage = view.findViewById(R.id.btn_photo)
        btnDeleteImage = view.findViewById(R.id.btn_remove_photo)

        btnEditImage.setOnClickListener {
            chooseImageGallery()
        }

        btnDeleteImage.setOnClickListener {
            imagePet.setImageDrawable(null)
        }

        btnAddPet.setOnClickListener {
            val name = namePet.text.toString()
            val breed = breedPet.text.toString()
            val age = agePet.text.toString().toInt()
            val imageURL = imagePet.toString()
            val pet = Pet(name, breed,age, imageURL)

            viewModel.addPetToUser(pet, currentUser!!, activity)
            namePet.text.clear()
            agePet.text.clear()
            breedPet.text.clear()
        }
        return view
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, OK_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == OK_GALLERY){

                imageUri = data?.data
                imagePet.setImageURI(imageUri)
            }
        }
    }

}
