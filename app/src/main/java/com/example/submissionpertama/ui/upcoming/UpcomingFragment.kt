package com.example.submissionpertama.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionpertama.data.Result
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.databinding.FragmentUpcomingBinding
import com.example.submissionpertama.helper.ViewModelFactory

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory: ViewModelFactory? = ViewModelFactory.getInstance()
        val upcomingViewModel: UpcomingViewModel by viewModels { factory!! }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUpcomingEvent.layoutManager = layoutManager

        upcomingViewModel.fetchUpcomingEvents().observe(viewLifecycleOwner) { result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        setEventData(result.data)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return root
    }

    private fun setEventData(upcomingEvents: List<EventItem>) {
        binding.progressBar.visibility = View.GONE
        val adapter = EventAdapter()
        adapter.submitList(upcomingEvents)
        binding.rvUpcomingEvent.adapter = adapter
        if(upcomingEvents.isEmpty()){
            binding.tvEmpty.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}