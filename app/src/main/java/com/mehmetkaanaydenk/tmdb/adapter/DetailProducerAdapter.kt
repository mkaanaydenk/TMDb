package com.mehmetkaanaydenk.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetkaanaydenk.tmdb.databinding.DetailProducerRowBinding
import com.mehmetkaanaydenk.tmdb.model.Crew

class DetailProducerAdapter(val producerList: List<Crew>):RecyclerView.Adapter<DetailProducerAdapter.ProducerHolder>() {
    class ProducerHolder(val binding: DetailProducerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProducerHolder {
        val binding= DetailProducerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProducerHolder(binding)
    }

    override fun getItemCount(): Int {
        return producerList.size
    }

    override fun onBindViewHolder(holder: ProducerHolder, position: Int) {
        holder.binding.producerNameText.text = producerList[position].name
    }
}