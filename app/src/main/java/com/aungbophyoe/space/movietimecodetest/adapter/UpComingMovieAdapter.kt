package com.aungbophyoe.space.movietimecodetest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aungbophyoe.space.movietimecodetest.databinding.RvMovieUpcomingItemBinding
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.utility.ImageBinderAdapter

class UpComingMovieAdapter(
    private val context: Context,
    private val itemCardOnClickListener: ItemCardOnClickListener
) : ListAdapter<MovieCacheEntity, UpComingMovieAdapter.ViewHolder>(UpComingMovieAdapter.DiffUtils) {

    class ViewHolder(private val binding: RvMovieUpcomingItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: MovieCacheEntity){
            binding.apply {
                ImageBinderAdapter.setImageUrl(ivImage,movie.poster_path)
                tvTitle.text = movie.title
                tvVote.text = "${movie.vote_average * 10} %"
                tvDescription.text = movie.overview
            }
        }
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    interface ItemCardOnClickListener{
        fun itemCardOnClick(id:Int)
    }

    object DiffUtils : DiffUtil.ItemCallback<MovieCacheEntity>(){
        override fun areItemsTheSame(oldItem: MovieCacheEntity, newItem: MovieCacheEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieCacheEntity, newItem: MovieCacheEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemCount(): Int {
        return currentList.size
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
                itemCardOnClickListener.itemCardOnClick(currentList[position].id)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = currentList[position]
        if(movie!=null){
            holder.bind(movie)
        }
    }
}