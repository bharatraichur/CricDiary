package com.codebhar.cricdiary.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codebhar.cricdiary.MatchesViewModelFactory
import com.codebhar.cricdiary.R
import com.codebhar.cricdiary.databinding.FragmentMatchesBinding

class MatchesFragment : Fragment() {

    private lateinit var binding: FragmentMatchesBinding

    private val viewModel: MatchesViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MatchesViewModelFactory(activity.application))[MatchesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_matches, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val matchesAdapter = MatchesAdapter()
        binding.currentMatchesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = matchesAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.status.observe(viewLifecycleOwner) {
            binding.matchesProgress.isVisible = it == MatchesViewModel.MatchesApiStatus.LOADING
        }

        viewModel.dataStatus.observe(viewLifecycleOwner) {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MatchesFragment()
    }
}