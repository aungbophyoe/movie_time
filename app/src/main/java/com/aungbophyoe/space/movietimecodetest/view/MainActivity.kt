package com.aungbophyoe.space.movietimecodetest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungbophyoe.space.movietimecodetest.adapter.PopularMovieAdapter
import com.aungbophyoe.space.movietimecodetest.adapter.UpComingMovieAdapter
import com.aungbophyoe.space.movietimecodetest.databinding.ActivityMainBinding
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import com.aungbophyoe.space.movietimecodetest.utility.showOrGone
import com.aungbophyoe.space.movietimecodetest.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),PopularMovieAdapter.ItemOnClickListener
    ,UpComingMovieAdapter.ItemCardOnClickListener{
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding
    private val homeViewModel : HomeViewModel by viewModels()
    private val popularMovieAdapter : PopularMovieAdapter by lazy {
        PopularMovieAdapter(this,this)
    }
    private val upComingMovieAdapter : UpComingMovieAdapter by lazy {
        UpComingMovieAdapter(this,this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding!!.root
        setContentView(view)
        intiRecyclerView()
        homeViewModel.getData()
        homeViewModel.getUpComingMovieData()
        observeData()
        binding!!.apply {
            rlTryAgain.setOnClickListener {
                homeViewModel.getData()
                homeViewModel.getUpComingMovieData()
            }
        }
    }

    private fun observeData(){
        binding!!.apply {
            homeViewModel.data.observe(this@MainActivity){
                it?.let { dataState ->
                    when(dataState){
                        is DataState.Loading -> {
                            progressBar.showOrGone(true)
                            rlTryAgain.showOrGone(false)
                            tvError.showOrGone(false)
                        }
                        is DataState.Success -> {
                            progressBar.showOrGone(false)
                            rlTryAgain.showOrGone(false)
                            tvError.showOrGone(false)
                            val data = dataState.data
                            if(data!=null){
                                popularMovieAdapter.submitList(data)
                                rvPopular.adapter!!.notifyDataSetChanged()
                            }
                        }
                        is DataState.Error -> {
                            tvError.text = "${dataState.exception.message}"
                            progressBar.showOrGone(false)
                            rlTryAgain.showOrGone(false)
                            tvError.showOrGone(true)
                        }
                        is DataState.TryAgain -> {
                            progressBar.showOrGone(false)
                            rlTryAgain.showOrGone(true)
                            tvError.showOrGone(false)
                        }
                    }
                }
            }

            homeViewModel.upComingMovieData.observe(this@MainActivity){
                it?.let { dataState ->
                    when(dataState){
                        is DataState.Loading -> {
                            progressBar.showOrGone(true)
                            rlTryAgain.showOrGone(false)
                            tvError.showOrGone(false)
                        }
                        is DataState.Success -> {
                            progressBar.showOrGone(false)
                            rlTryAgain.showOrGone(false)
                            tvError.showOrGone(false)
                            val data = dataState.data
                            if(data!=null){
                                upComingMovieAdapter.submitList(data)
                                rvUpComing.adapter!!.notifyDataSetChanged()
                            }
                        }
                        is DataState.Error -> {
                            tvError.text = "${dataState.exception.message}"
                            progressBar.showOrGone(false)
                            rlTryAgain.showOrGone(false)
                            tvError.showOrGone(true)
                        }
                        is DataState.TryAgain -> {
                            progressBar.showOrGone(false)
                            rlTryAgain.showOrGone(true)
                            tvError.showOrGone(false)
                        }
                    }
                }
            }
        }
    }

    private fun intiRecyclerView() {
        binding!!.apply {
            rvPopular.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                adapter = popularMovieAdapter
            }
            rvUpComing.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = upComingMovieAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun goDetail(id: Int){
        val intent = Intent(this,DetailActivity::class.java).apply {
            this.putExtra("id",id)
            startActivity(this)
        }
    }

    override fun itemOnClick(id: Int) {
        goDetail(id)
    }

    override fun itemCardOnClick(id: Int) {
        goDetail(id)
    }

}