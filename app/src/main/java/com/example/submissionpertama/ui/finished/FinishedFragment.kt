package com.example.submissionpertama.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionpertama.data.Result
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.databinding.FragmentFinishedBinding
import com.example.submissionpertama.helper.ViewModelFactory

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private val factory: ViewModelFactory? = ViewModelFactory.getInstance()
    private val viewModel: FinishedViewModel by viewModels { factory!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root




        with(binding){
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(value: String?): Boolean {
                    if (value != null){
                        fetchEvents(value)
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
            searchView.setOnCloseListener {
                fetchEvents()
                false
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFinishedEvent.layoutManager = layoutManager

        fetchEvents()

        return root
    }

    private fun fetchEvents(q: String = ""){
        viewModel.fetchFinishedEvents(q).observe(viewLifecycleOwner) { result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        binding.progressBarFinishedEvent.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        setEventData(result.data)
                    }
                    is Result.Error -> {
                        binding.progressBarFinishedEvent.visibility = View.GONE
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setEventData(finishedEvents: List<EventItem>) {
        binding.progressBarFinishedEvent.visibility = View.GONE
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