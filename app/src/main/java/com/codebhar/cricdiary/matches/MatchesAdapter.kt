package com.codebhar.cricdiary.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codebhar.cricdiary.database.CricMatchesData
import com.codebhar.cricdiary.databinding.ItemMatchBinding

class MatchesAdapter: ListAdapter<CricMatchesData, MatchesAdapter.ViewHolder>(MatchesDataDiffCallBack()) {

    class ViewHolder private constructor(
        private var binding: ItemMatchBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(matches: CricMatchesData) {
            binding.matches = matches
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMatchBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MatchesDataDiffCallBack: DiffUtil.ItemCallback<CricMatchesData>() {
    override fun areItemsTheSame(oldItem: CricMatchesData, newItem: CricMatchesData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CricMatchesData, newItem: CricMatchesData): Boolean {
        return oldItem == newItem
    }

}