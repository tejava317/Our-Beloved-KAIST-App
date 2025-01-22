package com.example.ourbelovedkaist

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import android.util.Base64
import android.util.Log
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.create_capsule_activity_button)
        val button2 = findViewById<Button>(R.id.join_capsule_activity_button)
        val button3 = findViewById<Button>(R.id.open_capsule_activity_button)

        button1.setOnClickListener {
            val intent = Intent(this, CreateCapsuleActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, JoinCapsuleActivity::class.java)
            startActivity(intent)
        }

        button3.setOnClickListener {
            val intent = Intent(this, OpenCapsuleActivity::class.java)
            startActivity(intent)
        }

        try {
            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val key = String(Base64.encode(md.digest(), 0))
                Log.d("KeyHash", "Key hash: $key")
            }
        } catch (e: Exception) {
            Log.e("KeyHash", "Error getting key hash", e)
        }
    }
}
