package com.example.iutilities

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log.d
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sell.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Sell : AppCompatActivity() {


    public var currentPhotoPath: String = ""
    public var uri: Uri? = null
    public var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        photoSelect.setOnClickListener {
            val photoFile: File? = createImageFile()
            photoFile?.also { uri = FileProvider.getUriForFile(this, "com.example.iutilities.fileprovider", it)
                d("IUTils", "File created. Uri = $uri")
                val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(intent, 69)
            }

        }

        sell_button_sell.setOnClickListener {
            if ( name_text.text == null || price_text.text == null || cat_text.text == null ) {
                d("IUTils", "Please enter the name, the price and the category and a brief description of the item you're selling")
                Toast.makeText(
                    this,
                    "Please enter at least the name, the price and the categor of the item you're selling",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                val storef = FirebaseStorage.getInstance().getReference("/sellphotos/${cat_text.text.toString()}/${name_text.text.toString()}")
                storef.putFile(uri!!)
                    .addOnSuccessListener {
                        storef.downloadUrl
                            .addOnSuccessListener {
                                url = it.toString()
                                d("IUTils", "url within success listener = $url")
                                val ref = FirebaseDatabase.getInstance().getReference("sell/${cat_text.text.toString()}")
                                val item = Item(cat_text.text.toString(), name_text.text.toString(), price_text.text.toString(), desc_text.text.toString(), url)
                                ref.child("${name_text.text.toString()}").setValue(item)
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Pushing failed. ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Pushed item successfully", Toast.LENGTH_SHORT).show()
                                        d("IUTils", "name = ${name_text.text.toString()}, photo url = $url")
                                    }
                            }
                    }
                    .addOnFailureListener {
                        d("IUTils", "${it.message}")
                    }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 69)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                d("IUTils", "User completed action")
                if ( data != null )
                {
                    d("IUTils", "Data not null")
                    itemImage.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, uri))
                    sell_button_sell.setText("")
                    sell_button_sell.alpha = 0f
                }
                else
                {
                    d("IUTils", "No data found")
                }
            }
            else
            {
                d("IUTils", "User cancelled activity")
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
}


class Item(val category: String, val name: String, val price: String, val description: String, val photourl: String)
{
    constructor():this("", "", "", "", "")
}