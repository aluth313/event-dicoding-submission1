package com.example.submissionpertama.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionpertama.data.response.EventItem
import com.example.submissionpertama.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinishedEventHome.layoutManager = layoutManager
        val layoutManagerUpcoming =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcomingEventHome.layoutManager = layoutManagerUpcoming

        homeViewModel.listEvent.observe(viewLifecycleOwner) { finishedEvents ->
            setEventData(finishedEvents)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }

        homeViewModel.listUpcomingEvent.observe(viewLifecycleOwner) { upcomingEvents ->
            setUpcomingEventData(upcomingEvents)
        }

        homeViewModel.isLoadingUpcoming.observe(viewLifecycleOwner) {
            showLoadingUpcoming(it)
        }

        homeViewModel.errorMessageUpcoming.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }
        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFinishedEventHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setEventData(finishedEvents: List<EventItem>) {
        val adapter = FinishedEventHomeAdapter()
        adapter.submitList(finishedEvents)
        binding.rvFinishedEventHome.adapter = adapter
        if (finishedEvents.isEmpty()) {
            binding.tvEmptyFinishedHome.visibility = View.VISIBLE
        }
    }

    private fun showLoadingUpcoming(isLoading: Boolean) {
        binding.progressBarUpcomingEventHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUpcomingEventData(upcomingEvents: List<EventItem>) {
        val adapter = UpcomingEventHomeAdapter()
        adapter.submitList(upcomingEvents)
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