package com.codebhar.cricdiary.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codebhar.cricdiary.database.CricSeriesData
import com.codebhar.cricdiary.databinding.ItemSeriesBinding

class SeriesAdapter: ListAdapter<CricSeriesData, SeriesAdapter.ViewHolder>(SeriesDataDiffCallBack()) {

    class ViewHolder private constructor(
        private var binding: ItemSeriesBinding
    ): RecyclerView.ViewHolder(binding.root) {
            fun bind(series: CricSeriesData) {
                binding.series = series
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSeriesBinding.inflate(layoutInflater, parent, false)

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

class SeriesDataDiffCallBack: DiffUtil.ItemCallback<CricSeriesData>() {
    override fun areItemsTheSame(oldItem: CricSeriesData, newItem: CricSeriesData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CricSeriesData, newItem: CricSeriesData): Boolean {
        return oldItem == newItem
    }

}