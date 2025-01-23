package com.example.ourbelovedkaist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ourbelovedkaist.data.model.Memory

class MemoryCircleAdapter(
    private val memories: List<Memory>,
    private val onItemClick: (Memory) -> Unit
) : RecyclerView.Adapter<MemoryCircleAdapter.MemoryCircleViewHolder>() {

    class MemoryCircleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val circleView: View = itemView.findViewById(R.id.circle_view)
        val memoryText: TextView = itemView.findViewById(R.id.memory_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryCircleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_memory_circle, parent, false)
        return MemoryCircleViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoryCircleViewHolder, position: Int) {
        val memory = memories[position]
        holder.memoryText.text = memory.type // 메모리 타입 또는 표시하고 싶은 텍스트
        holder.circleView.setOnClickListener {
            onItemClick(memory)
        }
    }

    override fun getItemCount(): Int = memories.size
}
