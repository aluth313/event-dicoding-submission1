package com.example.submissionpertama.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionpertama.data.response.EventItem
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
            ViewModelProvider(this)[FinishedViewModel::class.java]

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root


        with(binding){
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(value: String?): Boolean {
                    if (value != null){
                        finishedViewModel.fetchFinishedEvents(active = "0", q = value.toString())
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
            searchView.setOnCloseListener {
                finishedViewModel.fetchFinishedEvents()
                false
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinishedEvent.layoutManager = layoutManager

        finishedViewModel.listEvent.observe(viewLifecycleOwner) { finishedEvents ->
            setEventData(finishedEvents)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        finishedViewModel.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFinishedEvent.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setEventData(finishedEvents: List<EventItem>) {
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