package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_my_tuitions.*


class MyTuitions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tuitions)

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

        delete_button.setOnClickListener {
            val deleteref = FirebaseDatabase.getInstance().getReference("Tuitions").child("${offer_temp2.students_address.toString()}")
            deleteref.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            finish()
        }
    }
}
