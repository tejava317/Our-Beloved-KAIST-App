package com.example.ourbelovedkaist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ourbelovedkaist.data.model.Memory
import com.example.ourbelovedkaist.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowMemoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MemoryCircleAdapter
    private val memories = mutableListOf<Memory>()

    private val capsuleId = "2127aebb-9284-40db-a2c3-464786e81ccf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_memory)

        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )

        recyclerView = findViewById(R.id.recycler_view_memories)
        adapter = MemoryCircleAdapter(memories) { memory ->
            showMemoryDialog(memory)
        }
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3열로 그리드 배치
        recyclerView.adapter = adapter

//        val capsuleId = intent.getStringExtra("capsuleId") ?: return
        fetchMemories(capsuleId)
    }

    private fun fetchMemories(capsuleId: String) {
        RetrofitClient.apiService.getMemories(capsuleId).enqueue(object : Callback<List<Memory>> {
            override fun onResponse(call: Call<List<Memory>>, response: Response<List<Memory>>) {
                if (response.isSuccessful) {
                    val fetchedMemories = response.body()
                    if (fetchedMemories != null) {
                        memories.clear()
                        memories.addAll(fetchedMemories)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        this@ShowMemoryActivity,
                        "메모리 조회 실패: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Memory>>, t: Throwable) {
                Log.e("ShowMemoryActivity", "네트워크 오류: ${t.message}")
                Toast.makeText(
                    this@ShowMemoryActivity,
                    "네트워크 오류: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showMemoryDialog(memory: Memory) {
        AlertDialog.Builder(this)
            .setTitle("추억")
            .setMessage(memory.content)
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
