package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buy_item.*

class BuyItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_item)

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
            Picasso.get().load("${item_tmptmp.photourl.toString()}").into(itemImage)
        }
    }
}
