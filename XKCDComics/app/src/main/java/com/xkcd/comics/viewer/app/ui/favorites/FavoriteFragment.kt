package com.xkcd.comics.viewer.app.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xkcd.comics.viewer.app.R
import com.xkcd.comics.viewer.app.databinding.FragmentFavoriteListBinding

/**
 * A fragment representing a list of Comics marked as favorite.
 */
class FavoriteFragment : Fragment() {

    private var columnCount = 1
    private lateinit var viewModel: FavoriteComicsViewModel
    private lateinit var favoritesAdapter: MainAdapter
    private lateinit var binding: FragmentFavoriteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
        )[FavoriteComicsViewModel::class.java]

        favoritesAdapter = MainAdapter()
        // Set the adapter

        with(binding.favoritesList) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = favoritesAdapter
        }


        viewModel.getAllComicss().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.noDataFound.visibility = View.VISIBLE
                binding.favoritesList.visibility = View.GONE
            } else {
                binding.noDataFound.visibility = View.GONE
                binding.favoritesList.visibility = View.VISIBLE
                favoritesAdapter.submitToComicsList(it as ArrayList)
            }
        })
    }
}