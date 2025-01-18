package com.example.submissionpertama.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionpertama.data.response.EventsItem
import com.example.submissionpertama.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val finishedViewModel =
            ViewModelProvider(this).get(FinishedViewModel::class.java)

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinishedEvent.layoutManager = layoutManager

        finishedViewModel.listEvent.observe(viewLifecycleOwner) { finishedEvents ->
            setEventData(finishedEvents)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFinishedEvent.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setEventData(finishedEvents: List<EventsItem>) {
        val adapter = FinishedEventAdapter()
        adapter.submitList(finishedEvents)
        binding.rvFinishedEvent.adapter = adapter
        if(finishedEvents.isEmpty()){
            binding.tvEmptyFinished.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}