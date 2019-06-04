package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.email_text
import kotlinx.android.synthetic.main.activity_register.gmail_login
import kotlinx.android.synthetic.main.activity_register.password_text
import kotlinx.android.synthetic.main.activity_register.register_button
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.*

class Register : AppCompatActivity() {
    private var callbackManager: CallbackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var loginFacebook= findViewById<Button>(R.id.login_button)
        loginFacebook.setOnClickListener(View.OnClickListener {
            callbackManager =CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                        startActivity(Intent(applicationContext, Profile::class.java))
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")

                    }
                })
        })
        register_button.setOnClickListener {
            registerUser()
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

