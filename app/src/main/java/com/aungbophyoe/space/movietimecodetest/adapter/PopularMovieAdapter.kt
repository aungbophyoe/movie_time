package com.aungbophyoe.space.movietimecodetest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aungbophyoe.space.movietimecodetest.databinding.RvMoviePopularItemBinding
import com.aungbophyoe.space.movietimecodetest.model.Movie
import com.aungbophyoe.space.movietimecodetest.utility.ImageBinderAdapter

class PopularMovieAdapter(
    private val context: Context,
    private val itemOnClickListener: ItemOnClickListener
) : ListAdapter<Movie,PopularMovieAdapter.ViewHolder>(DiffUtils){

    class ViewHolder(private val binding: RvMoviePopularItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie){
            binding.apply {
                ImageBinderAdapter.setImageUrl(ivImage,movie.poster_path)
                tvTitle.text = movie.title
                tvVote.text = "${movie.vote_average * 10} %"
            }
        }
    }
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    interface ItemOnClickListener{
        fun itemOnClick(id:Int)
    }

    object DiffUtils : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(RvMoviePopularItemBinding.inflate(inflater,parent,false))
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemOnClickListener.itemOnClick(currentList[position].id)
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