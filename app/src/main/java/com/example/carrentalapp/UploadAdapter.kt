package com.example.carrentalapp
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class UploadAdapter(var context: Context, var data:ArrayList<Uploads>):BaseAdapter() {
    private class ViewHolder(row:View?){
        val mCarModel:TextView
        val mLocation:TextView
        val mAmount:TextView
        val mPhone:TextView
        val btnUpdate: Button
        val btnDelete:Button
        val addImage:ImageView
        val addCardView: CardView
        init {
            this.mCarModel = row?.findViewById(R.id.mCarModel) as TextView
            this.mLocation = row?.findViewById(R.id.mLocation) as TextView
            this.mAmount = row?.findViewById(R.id.mAmountPerDay) as TextView
            this.mPhone = row?.findViewById(R.id.mPhoneNumber) as TextView
            this.btnUpdate = row?.findViewById(R.id.btnUpdate) as Button
            this.btnDelete = row?.findViewById(R.id.btnDelete) as Button
            this.addImage = row?.findViewById(R.id.addImage) as ImageView
            this.addCardView = row?.findViewById(R.id.addCardView) as CardView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.add_vehicle_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:Uploads = getItem(position) as Uploads
        viewHolder.mCarModel.text = item.uploadName
        viewHolder.mLocation.text = item.uploadLocation
        viewHolder.mAmount.text = item.uploadAmount
        viewHolder.mPhone.text = item.uploadPhoneNumber
        Glide.with(context).load(item.uploadImage).into(viewHolder.addImage)
        viewHolder.btnUpdate.setOnClickListener {
            var intent = Intent(context,UpdateActivity::class.java)
            intent.putExtra("it",item.uploadImage)
            intent.putExtra("name",item.uploadName)
            intent.putExtra("amount",item.uploadAmount)
            intent.putExtra("location",item.uploadLocation)
            intent.putExtra("phoneNumber",item.uploadPhoneNumber)
            intent.putExtra("id",item.id)
            context.startActivity(intent)
        }
        viewHolder.btnDelete.setOnClickListener {
            var ref = FirebaseDatabase.getInstance().getReference().child("Cars/"+item.id)
            ref.removeValue().addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "Vehicle deleted successful", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}