package com.codebhar.cricdiary.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.codebhar.cricdiary.R
import com.codebhar.cricdiary.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exploreSeries.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_seriesFragment)
        }

        binding.exploreMatches.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_matchesFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}