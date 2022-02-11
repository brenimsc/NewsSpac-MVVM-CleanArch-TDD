package com.android.newsspace.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.newsspace.R
import com.android.newsspace.core.State
import com.android.newsspace.data.NewsCategory
import com.android.newsspace.databinding.HomeFragmentBinding
import com.android.newsspace.presentation.NewsAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {


    private lateinit var chipArticles: Chip
    private lateinit var chipBlogs: Chip
    private lateinit var chipReports: Chip
    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initBinding()
        setupChips()
        observerSnackBar()
        initRecyclerView()
        return binding.root
    }

    private fun setupChips() {

        chipArticles.setOnClickListener {
            viewModel.fetchPosts(NewsCategory.ARTICLE)
        }
        chipBlogs.setOnClickListener {
            viewModel.fetchPosts(NewsCategory.BLOGS)
        }
        chipReports.setOnClickListener {
            viewModel.fetchPosts(NewsCategory.REPORTS)
        }


    }

    private fun observerSnackBar() {
        viewModel.snackbar.observe(viewLifecycleOwner) {
            it?.let { error ->
                Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackBarShow()
            }
        }
    }

    private fun initRecyclerView() {

        val adapter = NewsAdapter()
        binding.homeRv.adapter = adapter

        viewModel.listNews.observe(viewLifecycleOwner) {
            when (it) {
                State.Loading -> {
                    viewModel.showProgressBar()
                }

                is State.Error -> {
                    viewModel.hideProgressBar()
                }

                is State.Success -> {
                    viewModel.hideProgressBar()
                    adapter.submitList(it.result)
                }

            }
        }


    }


    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        chipArticles = binding.chipArticle
        chipReports = binding.chipReports
        chipBlogs = binding.chipBlogs
    }


    /**
     * Esse companion object é código boilerplate que provavelmente
     * não será usado.
     */
    companion object {
        fun newInstance() = HomeFragment()
    }

}