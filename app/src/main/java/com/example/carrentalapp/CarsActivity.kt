package com.example.carrentalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.carrentalapp.databinding.ActivityCarsBinding
import com.example.carrentalapp.databinding.ActivityMainBinding


class CarsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(CarsHome(this))

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeNav -> replaceFragment(CarsHome(this))
                R.id.bookmarkNav -> replaceFragment(Bookmark())
                R.id.addNav -> replaceFragment(AddVehicle())
                R.id.settingsNav -> replaceFragment(Settings())

                else ->{

                }
            }
            true
        }


    }



    private fun replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mFrameLayout, fragment)
        fragmentTransaction.commit()
       }

}