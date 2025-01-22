package com.example.ourbelovedkaist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ourbelovedkaist.CapsuleRequest
import com.example.ourbelovedkaist.CapsuleResponse
import com.example.ourbelovedkaist.MainActivity
import com.example.ourbelovedkaist.R
import com.example.ourbelovedkaist.RetrofitClient
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
                // 요청 데이터 생성
                val capsuleRequest = CapsuleRequest(
                    name = capsuleName,
                    creator = creatorName
                )

                // API 호출
                RetrofitClient.instance.createCapsule(capsuleRequest).enqueue(object : Callback<CapsuleResponse> {
                    override fun onResponse(
                        call: Call<CapsuleResponse>,
                        response: Response<CapsuleResponse>
                    ) {
                        if (response.isSuccessful) {
                            val capsule = response.body()
                            Toast.makeText(
                                this@CreateCapsuleActivity,
                                "타임캡슐이 생성되었습니다: ${capsule?.id}",
                                Toast.LENGTH_LONG
                            ).show()

                            // MainActivity로 이동
                            val intent = Intent(this@CreateCapsuleActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@CreateCapsuleActivity, "생성 실패: ${response.message()}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<CapsuleResponse>, t: Throwable) {
                        Toast.makeText(this@CreateCapsuleActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
