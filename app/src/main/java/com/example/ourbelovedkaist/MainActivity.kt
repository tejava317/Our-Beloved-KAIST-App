package com.example.ourbelovedkaist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.create_capsule_button)
        val button2 = findViewById<Button>(R.id.join_capsule_button)

        button1.setOnClickListener {
            val intent = Intent(this, CreateCapsuleActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, JoinCapsuleActivity::class.java)
            startActivity(intent)
        }
    }
}
