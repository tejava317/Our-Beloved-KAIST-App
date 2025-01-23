package com.example.ourbelovedkaist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ourbelovedkaist.data.model.CapsuleRequest
import com.example.ourbelovedkaist.data.model.CapsuleResponse
import com.example.ourbelovedkaist.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                createCapsule(capsuleName, creatorName)
            }
        }
    }

    private fun createCapsule(name: String, creator: String) {
        val request = CapsuleRequest(name = name, creator = creator)

        RetrofitClient.apiService.createCapsule(request).enqueue(object : Callback<CapsuleResponse> {
            override fun onResponse(call: Call<CapsuleResponse>, response: Response<CapsuleResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Toast.makeText(this@CreateCapsuleActivity, "캡슐 생성 성공: ${responseBody?.id}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@CreateCapsuleActivity, "생성 실패: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CapsuleResponse>, t: Throwable) {
                Log.e("CreateCapsuleActivity", "네트워크 오류", t)
                Toast.makeText(this@CreateCapsuleActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
