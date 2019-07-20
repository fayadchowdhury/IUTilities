package com.example.iutilities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
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
            student_name.setText("Please teach ${offer_temp2.student_name.toString()}")
            student_address.setText("Living at ${offer_temp2.students_address.toString()}")
            institution_name.setText("Studying at ${offer_temp2.institution_name.toString()}")
            student_class.setText("In class${offer_temp2.students_class.toString()}")
            curriculum.setText("Under ${offer_temp2.curriculum.toString()}")
            description.setText("The request:\n${offer_temp2.description.toString()}")
            postername_tmp = offer_temp2.the_person_offering.toString()
        }

        delete_button.setOnClickListener {
            val deleteref = FirebaseFirestore.getInstance().collection("Tuitions").document("${offer_temp2.students_address.toString()}")
            deleteref.delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            finish()
        }
    }
}
