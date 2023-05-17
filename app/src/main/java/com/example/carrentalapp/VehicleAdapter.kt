package com.example.carrentalapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class VehicleAdapter(var context: Context, private val vehicleList: ArrayList<Vehicle>):
    RecyclerView.Adapter<VehicleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mTxtCarModel.text = vehicleList[position].carModel
        holder.mTxtLocation.text = vehicleList[position].location
        holder.mTxtAmount.text = vehicleList[position].amount
        holder.mTxtNumber.text = vehicleList[position].phoneNumber
        holder.mVehicleImage.setImageResource(vehicleList[position].vehicleImage)
        holder.mBookmarkIcon.setImageResource(vehicleList[position].bookmarkImage)

        holder.mTxtAmount.setOnClickListener {
            var intent = Intent(context,PaymentActivity::class.java)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mTxtCarModel:TextView = itemView.findViewById(R.id.mTxtCarModel)
        val mTxtLocation:TextView = itemView.findViewById(R.id.mTxtLocation)
        val mTxtAmount:TextView = itemView.findViewById(R.id.mTxtAmount)
        val mBookmarkIcon:ImageView = itemView.findViewById(R.id.mBookmarkIcon)
        val mVehicleImage:ImageView = itemView.findViewById(R.id.mVehicleImage)
        val mTxtNumber:TextView = itemView.findViewById(R.id.mTxtNumber)
        val mCardView:CardView = itemView.findViewById(R.id.mCardView)
    }

}