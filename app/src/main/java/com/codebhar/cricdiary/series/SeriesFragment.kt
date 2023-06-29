package com.codebhar.cricdiary.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codebhar.cricdiary.R
import com.codebhar.cricdiary.SeriesViewModelFactory
import com.codebhar.cricdiary.databinding.FragmentSeriesBinding

class SeriesFragment : Fragment() {

    private lateinit var binding: FragmentSeriesBinding

    private val viewModel: SeriesViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, SeriesViewModelFactory(activity.application))[SeriesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val seriesListAdapter = SeriesAdapter()
        binding.seriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = seriesListAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.status.observe(viewLifecycleOwner) {
            binding.seriesProgress.isVisible = it == SeriesViewModel.SeriesApiStatus.LOADING
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SeriesFragment()
    }
}