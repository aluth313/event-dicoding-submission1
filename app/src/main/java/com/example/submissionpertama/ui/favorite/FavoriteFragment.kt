package com.example.submissionpertama.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionpertama.databinding.FragmentFavoriteBinding
import com.example.submissionpertama.ui.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val favoriteViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        favoriteViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEventList ->
            if (favoriteEventList != null) {
                if (favoriteEventList.isEmpty()) {
                    binding.tvEmptyFavorite.visibility = View.VISIBLE
                    binding.progressBarFavoriteEvent.visibility = View.GONE
                } else {
                    adapter.setListFavoriteEvents(favoriteEventList)
                }
            }
        }

        adapter = FavoriteEventAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoriteEvent.layoutManager = layoutManager
        binding.rvFavoriteEvent.adapter = adapter

        return root
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory!!)[FavoriteViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val favoriteViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        favoriteViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEventList ->
            if (favoriteEventList != null) {
                if (favoriteEventList.isEmpty()) {
                    binding.tvEmptyFavorite.visibility = View.VISIBLE
                    binding.progressBarFavoriteEvent.visibility = View.GONE
                } else {
                    adapter.setListFavoriteEvents(favoriteEventList)
                }
            }
        }
    }
}