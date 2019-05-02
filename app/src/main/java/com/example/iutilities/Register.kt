package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener {
            registerUser()
        }

        facebook_login.setOnClickListener {
            Toast.makeText(this, "This is a WIP", Toast.LENGTH_SHORT).show()
        }

        gmail_login.setOnClickListener {
            Toast.makeText(this, "This is a WIP", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser()
    {
        val username = username_text.text.toString()
        val email = email_text.text.toString()
        val password = password_text.text.toString()
        val contact = contact_text.text.toString()

        if ( email.isEmpty() || password.isEmpty() || username.isEmpty() || contact.isEmpty() ) {
            Toast.makeText(
                this,
                "Please enter username, email address and password before registering",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                    val ref = FirebaseDatabase.getInstance().getReference("users")
                    val uid = FirebaseAuth.getInstance().uid
                    val user = User(username, email, password, contact)
                    ref.child("$uid").setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "User added to database successfully", Toast.LENGTH_SHORT).show()
                            val user_now = FirebaseAuth.getInstance().currentUser
                            user_now?.sendEmailVerification()
                                ?.addOnSuccessListener {
                                    Toast.makeText(this, "Email sent to $email", Toast.LENGTH_SHORT).show()
                                }
                            val intent = Intent(this, Profile::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    val username_update = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    val fb_user = FirebaseAuth.getInstance().currentUser
                    fb_user?.updateProfile(username_update)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "User not created. ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

class User(val username: String, val email: String, val password: String, val contact: String)