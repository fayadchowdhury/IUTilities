package com.example.iutilities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sign_in_button.setOnClickListener {
            signInUser()
        }
    }

    private fun signInUser()
    {
        val email = email_text.text.toString()
        val password = password_text.text.toString()

        if ( email.isEmpty() || password.isEmpty() )
        {
            Toast.makeText(this, "Please enter email address and password before signing in", Toast.LENGTH_SHORT).show()
        }
        else
        {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "User signed in successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Profile::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "User sign in failed. ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
