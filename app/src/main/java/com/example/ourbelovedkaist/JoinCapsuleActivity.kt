package com.example.ourbelovedkaist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ourbelovedkaist.data.model.MemoryRequest
import com.example.ourbelovedkaist.data.model.MemoryResponse
import com.example.ourbelovedkaist.data.network.RetrofitClient
import com.example.timecapsule.MapsActivity
import retrofit2.Call
import retrofit2.Response
import java.util.*

class JoinCapsuleActivity : AppCompatActivity() {

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var videoPickerLauncher: ActivityResultLauncher<Intent>

    private lateinit var selectedDate: String

    private val capsuleId = "2127aebb-9284-40db-a2c3-464786e81ccf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_capsule)

        val addTextButton = findViewById<LinearLayout>(R.id.text_button)
        val addImageButton = findViewById<LinearLayout>(R.id.image_button)
        val addVideoButton = findViewById<LinearLayout>(R.id.video_button)
        val packButton = findViewById<Button>(R.id.pack_button)

        initActivityResultLaunchers()

        addTextButton.setOnClickListener {
            showTextInputDialog()
        }

        addImageButton.setOnClickListener {
            showGalleryDialog(isImage = true)
        }

        addVideoButton.setOnClickListener {
            showGalleryDialog(isImage = false)
        }

        packButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun initActivityResultLaunchers() {
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri: Uri? = result.data?.data
                if (selectedImageUri != null) {
                    Toast.makeText(this, "이미지가 선택되었습니다.", Toast.LENGTH_SHORT).show()

                    // Send data to back-end database (API Implementation)
                    // ...
                }
            }
        }

        videoPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedVideoUri: Uri? = result.data?.data
                if (selectedVideoUri != null) {
                    Toast.makeText(this, "동영상이 선택되었습니다.", Toast.LENGTH_SHORT).show()

                    // Send data to back-end database (API Implementation)
                    // ...
                }
            }
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
                    createMemory(capsuleId, inputText)

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

    private fun showGalleryDialog(isImage: Boolean) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(if (isImage) "사진 추가" else "동영상 추가")
            .setMessage("갤러리에서 선택해주세요.")
            .setPositiveButton("갤러리에서 선택") { _, _ ->
                openGallery(isImage)
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun openGallery(isImage: Boolean) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = if (isImage) "image/*" else "video/*"
        if (isImage) {
            imagePickerLauncher.launch(intent)
        } else {
            videoPickerLauncher.launch(intent)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                showConfirmDialog()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showConfirmDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("포장하기")
            .setMessage("개봉할 날짜: $selectedDate\n\n타임캡슐을 포장하시겠습니까?")
            .setPositiveButton("포장하기") { _, _ ->
                navigateToPlaceCapsuleActivity()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun navigateToPlaceCapsuleActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("selected_date", selectedDate)
        startActivity(intent)
        finish()
    }

    private fun createMemory(capsuleId: String, content: String) {
        val memoryRequest = MemoryRequest(type = "TEXT", content = content, capsuleId = capsuleId)

        RetrofitClient.apiService.createMemory(capsuleId, memoryRequest)
            .enqueue(object : retrofit2.Callback<MemoryResponse> {
                override fun onResponse(call: Call<MemoryResponse>, response: Response<MemoryResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Toast.makeText(this@JoinCapsuleActivity, "메모리 생성 성공: ${responseBody?.id}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@JoinCapsuleActivity, "생성 실패: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MemoryResponse>, t: Throwable) {
                    Toast.makeText(this@JoinCapsuleActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
