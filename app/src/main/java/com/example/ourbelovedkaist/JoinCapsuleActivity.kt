package com.example.ourbelovedkaist

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JoinCapsuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_capsule)

        val addTextButton = findViewById<Button>(R.id.add_text_button)
        val addImageButton = findViewById<Button>(R.id.add_image_button)
        val addVideoButton = findViewById<Button>(R.id.add_video_button)
        val packButton = findViewById<Button>(R.id.pack_button)

        addTextButton.setOnClickListener {

        }

        addImageButton.setOnClickListener {

        }

        addVideoButton.setOnClickListener {

        }

        packButton.setOnClickListener {
            // Send data to back-end database (API Implementation)
            // ...

            Toast.makeText(this, "타임캡슐이 포장되었습니다.", Toast.LENGTH_LONG).show()

            Thread.sleep(1000)

            finish()
        }
    }
}
