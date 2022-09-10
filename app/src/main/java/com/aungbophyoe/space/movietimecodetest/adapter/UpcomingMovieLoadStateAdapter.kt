package com.aungbophyoe.space.movietimecodetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aungbophyoe.space.movietimecodetest.databinding.LoadStateViewBinding
import com.aungbophyoe.space.movietimecodetest.utility.showOrGone

class UpcomingMovieLoadStateAdapter(private val retryOnClickOnListener: RetryOnClickOnListener) : LoadStateAdapter<UpcomingMovieLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(val binding: LoadStateViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadStateViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    interface RetryOnClickOnListener{
        fun retryOnClick()
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binding.apply {
            when(loadState){
                is LoadState.Error -> {
                    progressBar.showOrGone(false)
                    rlTryAgain.showOrGone(true)
                    //tvErrorMessage.text = loadState.error.message
                }
                is LoadState.Loading -> {
                    progressBar.showOrGone(true)
                    rlTryAgain.showOrGone(false)
                }
                is  LoadState.NotLoading -> {
                 progressBar.showOrGone(false)
                }
            }
            rlTryAgain.setOnClickListener {
                retryOnClickOnListener.retryOnClick()
            }
        }
    }
}