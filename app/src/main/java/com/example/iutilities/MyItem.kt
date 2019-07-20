package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_item.*

class MyItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_item)

        var postername_tmp: String = ""
        val item_tmptmp = intent.getParcelableExtra<ItemObj>("ITEM")
        if ( item_tmptmp == null )
        {
            d("IUTils", "Error retrieving parcelable object")
        }
        else
        {
            name_text.setText(" ${item_tmptmp.name.toString()}")
            price_text.setText("BDT  ${item_tmptmp.price.toString()}")
            cat_text.setText("Category : ${item_tmptmp.category.toString()}")
            desc_text.setText(" ${item_tmptmp.description.toString()}")
            posterName.setText("Posted by  ${item_tmptmp.postername.toString()}")
            postername_tmp = item_tmptmp.postername.toString()
            Picasso.get().load("${item_tmptmp.photourl.toString()}").into(itemImage)
        }

        delete_button.setOnClickListener {
            val deleteref = FirebaseFirestore.getInstance().collection("sell").document("${item_tmptmp.name.toString()}")
            deleteref.delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            finish()
        }

    }
}
