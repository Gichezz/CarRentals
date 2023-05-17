package com.example.carrentalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var edtPassword:EditText
    private lateinit var btnReset:Button
    lateinit var btnCancel:Button

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        edtPassword = findViewById(R.id.editEmail)
        btnReset = findViewById(R.id.mBtnReset)
        btnCancel = findViewById(R.id.mBtnCancel)

        auth = FirebaseAuth.getInstance()

        btnReset.setOnClickListener {
            val newPassword = edtPassword.text.toString()
            auth.sendPasswordResetEmail(newPassword)
                .addOnSuccessListener {
                    Toast.makeText(this,"Please check your email",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                }
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}