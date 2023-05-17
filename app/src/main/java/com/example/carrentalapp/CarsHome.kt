package com.example.carrentalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarsHome(activity: CarsActivity) : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var vehicleAdapter:VehicleAdapter
    private var vehicleList = ArrayList<Vehicle>()
    var activity:CarsActivity
    init {
        this.activity = activity
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_cars_home, container, false)

        vehicleList = ArrayList()

        recyclerView = root.findViewById(R.id.vehicleList) as RecyclerView
        vehicleAdapter = VehicleAdapter(activity, vehicleList)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(context, 2 )
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = vehicleAdapter

        prepareVehicleListData()

        return root
    }

    private fun prepareVehicleListData() {
        var vehicle  = Vehicle("BMW M5 G-Power", "Nairobi", "Ksh20000/day", R.drawable.bmw_m5_img, R.drawable.ic_bookmark, "0712345678")
        vehicleList.add(vehicle)
        vehicle = Vehicle("Ford Mustang GT", "Nairobi", "Ksh22000/day", R.drawable.ford_mustang, R.drawable.ic_bookmark, "0734987654")
        vehicleList.add(vehicle)
        vehicle = Vehicle("Audi A7 2018", "Nairobi", "Ksh21000/day", R.drawable.audi, R.drawable.ic_bookmark, "0767850872")
        vehicleList.add(vehicle)
        vehicle = Vehicle("Mercedes-Benz SLS", "Nairobi", "Ksh24000/day", R.drawable.mercedes, R.drawable.ic_bookmark, "0723451698")
        vehicleList.add(vehicle)
        vehicle = Vehicle("Subaru Impreza", "Nairobi", "Ksh19000/day", R.drawable.subaru_img, R.drawable.ic_bookmark, "0754369102")
        vehicleList.add(vehicle)
        vehicle = Vehicle("Nissan X-Trail", "Nairobi", "Ksh25000/day", R.drawable.nissan_img, R.drawable.ic_bookmark, "0789654328")
        vehicleList.add(vehicle)

        vehicleAdapter.notifyDataSetChanged()
    }
}