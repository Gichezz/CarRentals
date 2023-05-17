package com.example.carrentalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class PaymentActivity : AppCompatActivity() {

    lateinit var proceed:TextView
    lateinit var mpesaTxt:TextView
    lateinit var btnMpesa:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        proceed = findViewById(R.id.proceedPayment)
        mpesaTxt = findViewById(R.id.mpesaTxt)
        btnMpesa = findViewById(R.id.mBtnMpesa)

        btnMpesa.setOnClickListener {
            val simToolKitLaunchIntent = applicationContext.packageManager.getLaunchIntentForPackage("com.android.stk")
            simToolKitLaunchIntent?.let { startActivity(it) }
        }
    }
}