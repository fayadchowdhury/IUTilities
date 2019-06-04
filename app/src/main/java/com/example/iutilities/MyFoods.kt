package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_my_foods.*

class MyFoods : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_foods)

        var postername_tmp: String = ""
        val food_tmptmp = intent.getParcelableExtra<FoodObj>("FOOD")
        if ( food_tmptmp == null ) {
            Log.d("IUTils", "Error retrieving parcelable object")
        } else {
            price_text.setText("Cost ${food_tmptmp.cost.toString()}")
            // price_text.setText("BDT  ${item_tmptmp.price.toString()}")
            name_text.setText("Restaurant name\n ${food_tmptmp.restname.toString()}")
            desc_text.setText("Description of food\n ${food_tmptmp.description.toString()}")
            posterName.setText("Posted by ${food_tmptmp.orderername.toString()}")
            postername_tmp = food_tmptmp.orderername.toString()
            //Picasso.get().load("${item_tmptmp.photourl.toString()}").into(itemImage)
        }

        delete_button.setOnClickListener {
            val deleteref = FirebaseDatabase.getInstance().getReference("foods").child("${food_tmptmp.restname.toString()} ${food_tmptmp.orderername.toString()}")
            deleteref.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            finish()
        }
    }
}
