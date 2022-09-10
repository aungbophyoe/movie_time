package com.aungbophyoe.space.movietimecodetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aungbophyoe.space.movietimecodetest.databinding.LoadStateViewBinding
import com.aungbophyoe.space.movietimecodetest.databinding.PopularMovieLoadStateViewBinding
import com.aungbophyoe.space.movietimecodetest.utility.showOrGone

class PopularMovieLoadStateAdapter : LoadStateAdapter<PopularMovieLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(val binding: PopularMovieLoadStateViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            PopularMovieLoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    interface RetryOnClickOnListener{
        fun retryOnClick()
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binding.apply {
            when(loadState){
                is LoadState.Error -> {
                    progressHorizontal.showOrGone(false)
                    //tvErrorMessage.text = loadState.error.message
                }
                is LoadState.Loading -> {
                    progressHorizontal.showOrGone(true)
                }
                is  LoadState.NotLoading -> {
                    progressHorizontal.showOrGone(false)
                }
            }
        }
    }
}