package com.aungbophyoe.space.movietimecodetest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.aungbophyoe.space.movietimecodetest.R
import com.aungbophyoe.space.movietimecodetest.databinding.ActivityDetailBinding
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import com.aungbophyoe.space.movietimecodetest.utility.ImageBinderAdapter
import com.aungbophyoe.space.movietimecodetest.utility.showOrGone
import com.aungbophyoe.space.movietimecodetest.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private var _binding :ActivityDetailBinding? = null
    private val binding get () = _binding
    private val detailViewModel : DetailViewModel by viewModels()
    private var flagFav : Boolean = false
    private val id : Int by lazy {
        intent.getIntExtra("id",610150)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = _binding!!.root
        setContentView(view)
        detailViewModel.getMovieDetail(id)
        observeApiData()
        binding!!.apply {
            ivFav.setOnClickListener {
                ivFav.setColorFilter(ContextCompat.getColor(this@DetailActivity,R.color.red))
                if(flagFav){
                    flagFav = false
                    ivFav.setColorFilter(ContextCompat.getColor(this@DetailActivity,R.color.gray))
                }else{
                    flagFav = true
                    ivFav.setColorFilter(ContextCompat.getColor(this@DetailActivity,R.color.red))
                }
                detailViewModel.setFavFlag(id,flagFav)
            }
            rlTryAgain.setOnClickListener {
                detailViewModel.getMovieDetail(id)
            }
        }
    }

    private fun observeApiData(){
        binding!!.apply {
         detailViewModel.data.observe(this@DetailActivity){
             it?.let { dataState ->
                 when(dataState){
                     is DataState.Loading -> {
                         tvError.showOrGone(false)
                         progressBar.showOrGone(true)
                         rlTryAgain.showOrGone(false)
                     }
                     is DataState.Success -> {
                         tvError.showOrGone(false)
                         progressBar.showOrGone(false)
                         rlTryAgain.showOrGone(false)
                         val data = dataState.data!!
                         tvTitle.text = data.title
                         tvDate.text = "${data.status} | ${data.release_date}"
                         tvTimeAndGenre.text = "${data.runtime} min   |   ${data.genres}"
                         tvDescription.text = data.overview
                         tvPercent.text = "${data.vote_average.toDouble() * 10} %"
                         tvVotes.text = "${data.vote_count} votes"
                         ImageBinderAdapter.setImageUrl(ivPoster,data.poster_path)
                         if(data.fav){
                             flagFav = data.fav
                             ivFav.setColorFilter(ContextCompat.getColor(this@DetailActivity,R.color.red))
                         }else{
                             flagFav = data.fav
                             ivFav.setColorFilter(ContextCompat.getColor(this@DetailActivity,R.color.gray))
                         }
                     }
                     is DataState.Error -> {
                         tvError.text = "${dataState.exception.message}"
                         progressBar.showOrGone(false)
                         rlTryAgain.showOrGone(false)
                         tvError.showOrGone(true)
                     }
                     is DataState.TryAgain -> {
                         tvError.showOrGone(false)
                         progressBar.showOrGone(false)
                         rlTryAgain.showOrGone(true)
                     }
                 }
             }
         }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}