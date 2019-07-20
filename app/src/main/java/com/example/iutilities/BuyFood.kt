package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buy_item.*

class BuyFood : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_food)


        var postername_tmp: String = ""
        val food_tmptmp = intent.getParcelableExtra<FoodObj>("FOOD")
        if ( food_tmptmp == null ) {
            Log.d("IUTils", "Error retrieving parcelable object")
        } else {
            price_text.setText("Estimated to cost around ${food_tmptmp.cost.toString()}")
            // price_text.setText("BDT  ${item_tmptmp.price.toString()}")
            name_text.setText("From ${food_tmptmp.restname.toString()}")
            desc_text.setText("The order:\n ${food_tmptmp.description.toString()}")
            posterName.setText("Requested by ${food_tmptmp.orderername.toString()}")
            postername_tmp = food_tmptmp.orderername.toString()
            //Picasso.get().load("${item_tmptmp.photourl.toString()}").into(itemImage)
        }
       contact_button.setOnClickListener {
//            Toast.makeText(this, "Contact button clicked", Toast.LENGTH_SHORT).show()
            val dialog = AlertDialog.Builder(this)
            val dialogview = layoutInflater.inflate(R.layout.contact_dialog, null)
//            val dbref = FirebaseDatabase.getInstance().getReference("users")
//            dbref.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    p0.children.forEach{
//                        val user_tmp = it.getValue(User::class.java)
//                        if ( user_tmp != null )
//                        {
//                            if ( user_tmp.username == postername_tmp )
//                            {
//                                dialogview.findViewById<TextView>(R.id.contact_username).setText(user_tmp.username.toString())
//                                dialogview.findViewById<TextView>(R.id.contact_phone).setText(user_tmp.contact.toString())
//                                dialogview.findViewById<TextView>(R.id.contact_email).setText(user_tmp.email.toString())
//                            }
//                        }
//                    }
//                }
//            })
           val dbref = FirebaseFirestore.getInstance().collection("users").whereEqualTo("username", postername_tmp)
           dbref.get()
               .addOnSuccessListener {
                   for ( doc in it )
                   {
                       val user_tmp = doc.toObject(User::class.java)
                       dialogview.findViewById<TextView>(R.id.contact_username).setText(user_tmp.username.toString())
                       dialogview.findViewById<TextView>(R.id.contact_phone).setText(user_tmp.contact.toString())
                       dialogview.findViewById<TextView>(R.id.contact_email).setText(user_tmp.email.toString())
                   }
               }

            dialog.setView(dialogview)
            dialog.setCancelable(true)
            val contactdialog = dialog.create()
            contactdialog.show()
       }

    }
}
