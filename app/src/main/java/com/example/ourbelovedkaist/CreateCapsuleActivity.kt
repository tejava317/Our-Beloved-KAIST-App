package com.example.ourbelovedkaist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateCapsuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_capsule)

        val etCapsuleName = findViewById<EditText>(R.id.et_capsule_name)
        val etCreatorName = findViewById<EditText>(R.id.et_creator_name)
        val createCapsuleButton = findViewById<Button>(R.id.create_capsule_button)

        createCapsuleButton.setOnClickListener {
            val capsuleName = etCapsuleName.text.toString().trim()
            val creatorName = etCreatorName.text.toString().trim()

            if (capsuleName.isEmpty() || creatorName.isEmpty()) {
                Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // Send data to back-end database (API Implementation)
                // ...

                Toast.makeText(this, "타임캡슐이 생성되었습니다.", Toast.LENGTH_LONG).show()

                Thread.sleep(1000)

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }
}
