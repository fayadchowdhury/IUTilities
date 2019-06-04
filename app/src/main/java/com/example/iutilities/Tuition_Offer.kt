package com.example.iutilities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.offer_tuitions.*

class Tuition_Offer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offer_tuitions)



        share_button.setOnClickListener {
            if (student_name.text == null || student_address.text == null || student_class.text == null || institution_name.text == null || curriculum.text == null || description.text == null ) {
                Toast.makeText(this, "Please enter the required text field", Toast.LENGTH_SHORT).show()
            } else {

                val name = FirebaseAuth.getInstance().currentUser?.displayName.toString()
                val offer = TuitionObj(
                    name, student_name.text.toString(), student_address.text.toString(), student_class.text.toString(),
                    institution_name.text.toString(), curriculum.text.toString(), description.text.toString()
                )

                val offernode = FirebaseDatabase.getInstance().getReference("Tuitions")
                offernode.child("${student_address.text.toString()}").setValue(offer)
                    .addOnFailureListener {
                        Toast.makeText(this, "Pushing unsuccessfull.", Toast.LENGTH_SHORT).show()
                    }
                    .addOnSuccessListener {
                        Toast.makeText(this, "Pushed item successfully.", Toast.LENGTH_SHORT).show()
                        finish()
                    }

            }
        }

    }
}
