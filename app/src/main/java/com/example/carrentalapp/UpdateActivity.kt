package com.example.carrentalapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.IOException
import java.util.UUID

class UpdateActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    lateinit var updateImage:ImageView
    lateinit var updateName:TextView
    lateinit var updateAmount:TextView
    lateinit var updateLocation:TextView
    lateinit var updateNumber:TextView
    lateinit var btnUpdate:Button
    lateinit var progress:ProgressDialog
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        updateImage = findViewById(R.id.updateImage)
        updateName = findViewById(R.id.updateName)
        updateAmount = findViewById(R.id.updateAmount)
        updateLocation = findViewById(R.id.updateLocation)
        updateNumber = findViewById(R.id.updatePhoneNumber)
        btnUpdate = findViewById(R.id.myUpdateBtn)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        progress = ProgressDialog(this)
        progress.setTitle("Updating")
        progress.setMessage("Please wait...")
        var receivedImage = intent.getStringExtra("it")
        var receivedName = intent.getStringExtra("name")
        var receivedAmount = intent.getStringExtra("amount")
        var receivedLocation = intent.getStringExtra("location")
        var receivedNumber = intent.getStringExtra("phoneNumber")
        var receivedId = intent.getStringExtra("id")
        updateName.setText(receivedName)
        updateAmount.setText(receivedAmount)
        updateLocation.setText(receivedLocation)
        updateNumber.setText(receivedNumber)
        Picasso.get().load(receivedImage).into(updateImage)
        updateImage.setOnClickListener { launchGallery() }
        btnUpdate.setOnClickListener {
            var name = updateName.text.toString().trim()
            var amount = updateAmount.text.toString().trim()
            var location = updateLocation.text.toString().trim()
            var phoneNumber = updateNumber.text.toString().trim()
            var id = receivedId
            if (name.isEmpty()){
                updateName.setError("Please fill this input")
                updateName.requestFocus()
            }else if (amount.isEmpty()){
                updateAmount.setError("Please fill this input")
                updateAmount.requestFocus()
            }else if (location.isEmpty()){
                updateLocation.setError("Please fill this input")
                updateLocation.requestFocus()
            }else if (phoneNumber.isEmpty()){
                updateNumber.setError("Please fill this input")
                updateNumber.requestFocus()
            }else{
                if(filePath != null){

                    val ref = storageReference?.child("Cars/" + UUID.randomUUID().toString())
                    progress.show()
                    val uploadTask = ref?.putFile(filePath!!)!!.addOnCompleteListener{
                        progress.dismiss()
                        if (it.isSuccessful){
                            ref.downloadUrl.addOnSuccessListener {
                                var carData = Uploads(it.toString(),name,amount,location,phoneNumber,id)
                                var ref = FirebaseDatabase.getInstance().getReference().child("Cars/"+id)
                                ref.setValue(carData)
                                Toast.makeText(this, "Vehicle updated successfully", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this, "Failed to update vehicle", Toast.LENGTH_SHORT).show()
                        }
                    }


                }else{
                    Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                updateImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
