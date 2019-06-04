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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_buy_item.*
import kotlinx.android.synthetic.main.buy_tuitions.*

class TuitionsDetailed  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_tuitions)

        var postername_tmp: String = ""

        val offer_temp2 = intent.getParcelableExtra<TuitionObj>("Offer")

        if (offer_temp2 == null) {
            Log.d("IUTils", "Error retrieving parcelable object")
            Toast.makeText(this, "ERROR RECEIVING PARCELABLE OBJECT", Toast.LENGTH_SHORT).show()
        } else {
            detailsstudent_name.setText("student_name:${offer_temp2.student_name.toString()}")
            detailsstudent_address.setText("students_address:${offer_temp2.students_address.toString()}")
            detailsinstitute.setText("institute:${offer_temp2.institution_name.toString()}")
            detailsstudent_class.setText("students_class:${offer_temp2.students_class.toString()}")
            detailsdesc.setText("description:${offer_temp2.description.toString()}")
            postername_tmp = offer_temp2.the_person_offering.toString()
        }

        contact_button2.setOnClickListener {
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