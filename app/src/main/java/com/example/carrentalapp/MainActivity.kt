package com.example.carrentalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var mCarCard:CardView
    lateinit var mTruckCard:CardView
    lateinit var mHeliCard:CardView
    lateinit var mBikeCard:CardView
    lateinit var mBoatCard:CardView
    lateinit var mLogout:CardView
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCarCard = findViewById(R.id.carCard)
        mTruckCard = findViewById(R.id.truckCard)
        mHeliCard = findViewById(R.id.heliCard)
        mBikeCard = findViewById(R.id.bikeCard)
        mBoatCard = findViewById(R.id.boatCard)
        mLogout = findViewById(R.id.logoutCard)
        auth = FirebaseAuth.getInstance()

        mCarCard.setOnClickListener {
            val carIntent = Intent(this, CarsActivity::class.java)
            startActivity(carIntent)
        }
        mTruckCard.setOnClickListener {
            val truckIntent = Intent(this, TrucksActivity::class.java)
            startActivity(truckIntent)
        }
        mHeliCard.setOnClickListener {
            val heliIntent = Intent(this, HelicoptersActivity::class.java)
            startActivity(heliIntent)
        }
        mBikeCard.setOnClickListener {
            val bikeIntent = Intent(this, BikesActivity::class.java)
            startActivity(bikeIntent)
        }
        mBoatCard.setOnClickListener {
            val boatIntent = Intent(this, BoatsActivity::class.java)
            startActivity(boatIntent)
        }
        mLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}