package com.example.ourbelovedkaist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.create_capsule_activity_button)
        val button2 = findViewById<Button>(R.id.join_capsule_activity_button)
        val button3 = findViewById<Button>(R.id.open_capsule_activity_button)
        //val mapButton = findViewById<Button>(R.id.map_activity_button)

        button1.setOnClickListener {
            val intent = Intent(this, CreateCapsuleActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, JoinCapsuleActivity::class.java)
            startActivity(intent)
        }

//        button3.setOnClickListener {
//            val intent = Intent(this, OpenCapsuleActivity::class.java)
//            startActivity(intent)
//        }

        findViewById<Button>(R.id.open_capsule_activity_button).setOnClickListener {
            val intent = Intent(this, MapsViewActivity::class.java)
            startActivity(intent)
        }

//        mapButton.setOnClickListener {
//            startActivity(Intent(this, MapsActivity::class.java))
//        }
    }
}
