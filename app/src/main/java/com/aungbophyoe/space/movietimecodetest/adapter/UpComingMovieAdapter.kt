package com.aungbophyoe.space.movietimecodetest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aungbophyoe.space.movietimecodetest.databinding.RvMovieUpcomingItemBinding
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.utility.ImageBinderAdapter

class UpComingMovieAdapter(
    private val context: Context,
    private val itemCardOnClickListener: ItemCardOnClickListener
) : PagingDataAdapter<MovieCacheEntity, UpComingMovieAdapter.ViewHolder>(UpComingMovieAdapter.DiffUtils) {

    class ViewHolder(private val binding: RvMovieUpcomingItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieCacheEntity){
            binding.apply {
                ImageBinderAdapter.setImageUrl(ivImage,movie.poster_path ?: "https://picsum.photos/200")
                tvTitle.text = movie.title
                val voteAvg = movie.vote_average ?: 1.0
                tvVote.text = "${voteAvg * 10} %"
                tvDescription.text = movie.overview
            }
        }
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    interface ItemCardOnClickListener{
        fun itemCardOnClick(id:String)
    }

    object DiffUtils : DiffUtil.ItemCallback<MovieCacheEntity>(){
        override fun areItemsTheSame(oldItem: MovieCacheEntity, newItem: MovieCacheEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieCacheEntity, newItem: MovieCacheEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            RvMovieUpcomingItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemCardOnClickListener.itemCardOnClick(getItem(position)!!.id)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if(movie!=null){
            holder.bind(movie)
        }
    }
}