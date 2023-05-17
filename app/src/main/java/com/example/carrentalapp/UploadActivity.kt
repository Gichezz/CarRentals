package com.example.carrentalapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class UploadActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_upload_image: Button
    lateinit var progress: ProgressDialog
    lateinit var edtName: EditText
    lateinit var edtAmount: EditText
    lateinit var edtLocation: EditText
    lateinit var edtPhoneNumber: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        btn_upload_image = findViewById(R.id.btnUpload)
        imagePreview = findViewById(R.id.uploadImage)
        edtName = findViewById(R.id.uploadName)
        edtAmount = findViewById(R.id.uploadAmount)
        edtLocation = findViewById(R.id.uploadLocation)
        edtPhoneNumber = findViewById(R.id.uploadPhoneNumber)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")

        imagePreview.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }
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
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage(){
        var name = edtName.text.toString().trim()
        var amount = edtAmount.text.toString().trim()
        var location = edtLocation.text.toString().trim()
        var phoneNumber = edtPhoneNumber.text.toString().trim()
        var id = System.currentTimeMillis().toString()
        if (name.isEmpty()){
            edtName.setError("Please fill this input")
            edtName.requestFocus()
        }else if (amount.isEmpty()){
            edtAmount.setError("Please fill this input")
            edtAmount.requestFocus()
        }else if (location.isEmpty()){
            edtLocation.setError("Please fill this input")
            edtLocation.requestFocus()
        }else if (phoneNumber.isEmpty()){
            edtPhoneNumber.setError("Please fill this input")
            edtPhoneNumber.requestFocus()
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
                            Toast.makeText(this, "Vehicle uploaded successfully", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "Failed to upload vehicle", Toast.LENGTH_SHORT).show()
                    }
                }


            }else{
                Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
            }
        }

    }

}