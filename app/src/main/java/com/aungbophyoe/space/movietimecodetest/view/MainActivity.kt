package com.aungbophyoe.space.movietimecodetest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungbophyoe.space.movietimecodetest.adapter.PopularMovieAdapter
import com.aungbophyoe.space.movietimecodetest.adapter.PopularMovieLoadStateAdapter
import com.aungbophyoe.space.movietimecodetest.adapter.UpComingMovieAdapter
import com.aungbophyoe.space.movietimecodetest.adapter.UpcomingMovieLoadStateAdapter
import com.aungbophyoe.space.movietimecodetest.databinding.ActivityMainBinding
import com.aungbophyoe.space.movietimecodetest.utility.Constants.isNetworkAvailable
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import com.aungbophyoe.space.movietimecodetest.utility.showOrGone
import com.aungbophyoe.space.movietimecodetest.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),PopularMovieAdapter.ItemOnClickListener
    ,UpComingMovieAdapter.ItemCardOnClickListener,UpcomingMovieLoadStateAdapter.RetryOnClickOnListener{
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding
    private val homeViewModel : HomeViewModel by viewModels()
    private val popularMovieAdapter : PopularMovieAdapter by lazy {
        PopularMovieAdapter(this,this)
    }
    private val upComingMovieAdapter : UpComingMovieAdapter by lazy {
        UpComingMovieAdapter(this,this)
    }

    private val upcomingMovieLoadStateAdapter : UpcomingMovieLoadStateAdapter by lazy {
        UpcomingMovieLoadStateAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        binding!!.apply {
            if(isNetworkAvailable(this@MainActivity)){
                rlTryAgain.showOrGone(false)
            }else{
                rlTryAgain.showOrGone(true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding!!.root
        setContentView(view)
        intiRecyclerView()
        observeData()
        binding!!.apply {
            rlTryAgain.setOnClickListener {
                if(isNetworkAvailable(this@MainActivity)){
                    popularMovieAdapter.retry()
                    upComingMovieAdapter.retry()
                    rlTryAgain.showOrGone(false)
                }
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun observeData(){
        binding!!.apply {
            lifecycleScope.launchWhenStarted {
                try {
                    homeViewModel.getAllPopularMovies().collectLatest { response->
                        popularMovieAdapter.submitData(response)
                    }
                }catch (e:Exception){
                    homeViewModel.getAllPopularMovies().cancellable()
                }
            }

            lifecycleScope.launchWhenStarted {
                try {
                    homeViewModel.getAllUpcomingMovies().collectLatest { response->
                        upComingMovieAdapter.submitData(response)
                    }
                }catch (e:Exception){
                    homeViewModel.getAllUpcomingMovies().cancellable()
                }
            }
        }
    }

    private fun intiRecyclerView() {
        binding!!.apply {
            rvPopular.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                adapter = popularMovieAdapter.withLoadStateFooter(PopularMovieLoadStateAdapter())
            }
            rvUpComing.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = upComingMovieAdapter.withLoadStateFooter(upcomingMovieLoadStateAdapter)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun goDetail(id: String){
        val intent = Intent(this,DetailActivity::class.java).apply {
            this.putExtra("id",id.toInt())
            startActivity(this)
        }
    }

    override fun itemOnClick(id: String) {
        goDetail(id)
    }

    override fun itemCardOnClick(id: String) {
        goDetail(id)
    }


    override fun retryOnClick() {
        if(isNetworkAvailable(this)){
            popularMovieAdapter.retry()
            upComingMovieAdapter.retry()
        }
    }

}