package com.example.carrentalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.carrentalapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.mBtnReg.setOnClickListener {
            val username = binding.mEdtName.text.toString()
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()
            val confirmPassword = binding.mEdtConfirmPassword.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (isPasswordValid(password)){
                    if (password == confirmPassword) {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)

                                    database = FirebaseDatabase.getInstance().getReference("Users")
                                    val user = Users(username, email, password, confirmPassword)
                                    database.child(username).setValue(user).addOnSuccessListener {
                                        binding.mEdtName.text.clear()
                                        binding.registerEmail.text.clear()
                                        binding.registerPassword.text.clear()
                                        binding.mEdtConfirmPassword.text.clear()

                                        Toast.makeText(this, "User Successfully Saved", Toast.LENGTH_SHORT).show()

                                    }.addOnFailureListener {
                                        Toast.makeText(this, "Failed to save user", Toast.LENGTH_SHORT).show()

                                    }
                                } else {
                                    Toast.makeText(
                                        this,
                                        it.exception.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Password has to be 8 or more characters", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        binding.mSignIn.setOnClickListener {
            val loginIntent = Intent(this,LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun isPasswordValid(password:String):Boolean{
        val minLength = 8 // Min required password length
        return password.length >=minLength
    }
}