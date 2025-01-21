package com.example.ourbelovedkaist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
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
            showTextInputDialog()
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

    private fun showTextInputDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_text, null)
        val editText = dialogView.findViewById<EditText>(R.id.et_input_text)

        val dialog = AlertDialog.Builder(this)
            .setTitle("텍스트 추가")
            .setView(dialogView)
            .setPositiveButton("확인") { _, _ ->
                val inputText = editText.text.toString().trim()
                if (inputText.isNotEmpty()) {
                    // Send data to back-end database (API Implementation)
                    // ...

                    Toast.makeText(this, "텍스트가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "텍스트를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}
