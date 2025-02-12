package com.example.submissionpertama.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionpertama.SettingPreferences
import com.example.submissionpertama.data.Result
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.dataStore
import com.example.submissionpertama.databinding.FragmentHomeBinding
import com.example.submissionpertama.helper.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val factory: ViewModelFactory? = ViewModelFactory.getInstance(requireActivity().application, pref)
        val homeViewModel: HomeViewModel by viewModels { factory!! }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinishedEventHome.layoutManager = layoutManager
        val layoutManagerUpcoming =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcomingEventHome.layoutManager = layoutManagerUpcoming

        //fetch finished events
        homeViewModel.getEvents("0").observe(viewLifecycleOwner) { result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        binding.progressBarFinishedEventHome.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBarFinishedEventHome.visibility = View.GONE
                        setEventData(result.data)
                    }
                    is Result.Error -> {
                        binding.progressBarFinishedEventHome.visibility = View.GONE
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        //fetch upcoming events
        homeViewModel.getUpcomingEvents("1").observe(viewLifecycleOwner) { result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        binding.progressBarUpcomingEventHome.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBarUpcomingEventHome.visibility = View.GONE
                        setUpcomingEventData(result.data)
                    }
                    is Result.Error -> {
                        binding.progressBarUpcomingEventHome.visibility = View.GONE
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return root
    }

    private fun setEventData(finishedEvents: List<EventItem>) {
        val adapter = FinishedEventHomeAdapter()
        adapter.submitList(finishedEvents.take(5))
        binding.rvFinishedEventHome.adapter = adapter
        if (finishedEvents.isEmpty()) {
            binding.tvEmptyFinishedHome.visibility = View.VISIBLE
        }
    }

    private fun setUpcomingEventData(upcomingEvents: List<EventItem>) {
        val adapter = UpcomingEventHomeAdapter()
        adapter.submitList(upcomingEvents.take(5))
        binding.rvUpcomingEventHome.adapter = adapter
        if (upcomingEvents.isEmpty()) {
            binding.tvEmptyUpcomingHome.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}