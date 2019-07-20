package com.example.iutilities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val uid = FirebaseAuth.getInstance().uid
            if ( uid != null )
            {
                val intent = Intent(this, Marketplace::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                val intent = Intent(this, LoginRegister::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)

    }
}
