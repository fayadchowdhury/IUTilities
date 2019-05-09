package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        contact_button.setOnClickListener {
            Toast.makeText(this, "Contact button clicked", Toast.LENGTH_SHORT).show()
            val dialog = AlertDialog.Builder(this)
            val dialogview = layoutInflater.inflate(R.layout.contact_dialog, null)
            val dbref = FirebaseDatabase.getInstance().getReference("users")
            dbref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach{
                        val user_tmp = it.getValue(User::class.java)
                        if ( user_tmp != null )
                        {
                            if ( user_tmp.username == postername_tmp )
                            {
                                dialogview.findViewById<TextView>(R.id.contact_username).setText(user_tmp.username.toString())
                                dialogview.findViewById<TextView>(R.id.contact_phone).setText(user_tmp.contact.toString())
                                dialogview.findViewById<TextView>(R.id.contact_email).setText(user_tmp.email.toString())
                            }
                        }
                    }
                }
            })
            dialog.setView(dialogview)
            dialog.setCancelable(true)
            val contactdialog = dialog.create()
            contactdialog.show()
        }
    }
}
