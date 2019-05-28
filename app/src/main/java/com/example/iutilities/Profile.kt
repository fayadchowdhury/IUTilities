package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val uid = FirebaseAuth.getInstance().uid

        if ( uid == null )
        {
            d("IUTils", "No UID found")
            val intent = Intent(this, LoginRegister::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        sign_out_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginRegister::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        mkt_button.setOnClickListener {
            val intent = Intent(this, Marketplace::class.java)
            startActivity(intent)
        }

        tuitions_button.setOnClickListener {
            val intent = Intent(this, Tuitions::class.java)
            startActivity(intent)
        }

        rides_button.setOnClickListener {
            val intent = Intent(this, Rides::class.java)
            startActivity(intent)
        }

        profile_button.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }
    }
}
