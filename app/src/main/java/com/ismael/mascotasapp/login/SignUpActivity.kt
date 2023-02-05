package com.ismael.mascotasapp.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ismael.mascotasapp.MainActivity
import com.ismael.mascotasapp.R


class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var  db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        val signUpButton = findViewById<AppCompatButton>(R.id.btn_register)
        signUpButton.setOnClickListener {
            val dni = findViewById<AppCompatEditText>(R.id.et_dni).text.toString()
            val name = findViewById<AppCompatEditText>(R.id.et_name).text.toString()
            val age = findViewById<AppCompatEditText>(R.id.et_age).text.toString()
            val email = findViewById<AppCompatEditText>(R.id.et_email).text.toString()
            val password = findViewById<AppCompatEditText>(R.id.et_password).text.toString()

            if(dni.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                val user = hashMapOf(
                    "DNI" to dni,
                    "Nombre" to name,
                    "Edad" to age,
                    "Correo_Electrónico" to email
                )

                db.collection("Users").add(user)


                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "USUARIO REGISTRADO CON ÉXITO", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))

                        } else {
                            Toast.makeText(this, "ERROR EN EL REGISTRO DE USUARIO", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "POR FAVOR, INTRODUZCA SU DNI, EMAIL Y CONTRASEÑA", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
