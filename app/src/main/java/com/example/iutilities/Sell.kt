package com.example.iutilities

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import android.util.Log.d
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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

        cat_select.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when(item.itemId)
                {
                    R.id.cat_tech ->
                    {
                        cat_text.setText("Tech")
                        true
                    }
                    R.id.cat_stationery ->
                    {
                        cat_text.setText("Stationery")
                        true
                    }
                    R.id.cat_furniture ->
                    {
                        cat_text.setText("Furniture")
                        true
                    }
                    R.id.cat_accessories ->
                    {
                        cat_text.setText("Accessories")
                        true
                    }
                    R.id.cat_clothes ->
                    {
                        cat_text.setText("Clothes")
                        true
                    }
                    R.id.cat_books ->
                    {
                        cat_text.setText("Books")
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.cat_menu)
            popupMenu.show()
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
                val poster = FirebaseAuth.getInstance().currentUser?.displayName.toString()
                val storef = FirebaseStorage.getInstance().getReference("/sellphotos/${cat_text.text.toString()}/${name_text.text.toString()}")
                storef.putFile(uri!!)
                    .addOnSuccessListener {
                        storef.downloadUrl
                            .addOnSuccessListener {
                                url = it.toString()
                                d("IUTils", "url within success listener = $url")
                                val ref = FirebaseDatabase.getInstance().getReference("sell/${cat_text.text.toString()}")
                                val item = ItemObj(cat_text.text.toString(), name_text.text.toString(), price_text.text.toString(), desc_text.text.toString(), url, poster)
                                ref.child("${name_text.text.toString()}").setValue(item)
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Pushing failed. ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Pushed item successfully", Toast.LENGTH_SHORT).show()
                                        d("IUTils", "name = ${name_text.text.toString()}, photo url = $url, poster_name = $poster")
                                        finish()
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
                    photoSelect.setText("")
                    //sell_button_sell.alpha = 0f
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