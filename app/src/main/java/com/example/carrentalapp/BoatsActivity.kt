package com.example.carrentalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.carrentalapp.databinding.ActivityBikesBinding

class BoatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBikesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBikesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(BoatsHome())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeNav -> replaceFragment(BoatsHome())
                R.id.bookmarkNav -> replaceFragment(Bookmark())
                R.id.addNav -> replaceFragment(AddVehicle())
                R.id.settingsNav -> replaceFragment(Settings())

                else ->{

                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mFrameLayout, fragment)
        fragmentTransaction.commit()
    }
}