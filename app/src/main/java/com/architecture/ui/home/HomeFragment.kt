package com.architecture.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.architecture.R
import com.architecture.base.*
import com.architecture.databinding.FragmentHomeBinding
import com.architecture.ui.activity.HomeActivity
import com.architecture.ui.home.adapter.SearchAdapter
import com.architecture.ui.home.models.Menus
import com.architecture.ui.home.models.RestaurantWithMenu
import com.architecture.ui.home.models.Restaurants
import com.architecture.util.Util

@SuppressLint("FragmentLiveDataObserve")
class HomeFragment : BaseFragment() {

    lateinit var mViewModel: HomeViewModel
    lateinit var mBinding: FragmentHomeBinding

    var adapter = SearchAdapter(R.layout.raw_restro)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
        }

        return mBinding.root
    }

    private fun setupRecycler() {
        mBinding.recycler.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWork()
        performSearch()
        setData()
    }

    private fun setData() {
        mViewModel.getAllItemsLiveData()?.observe(this, Observer {
            adapter.setNewItems(it as List<RestaurantWithMenu>)
        })
    }

    private fun performSearch() {
        mBinding.edt.onTextChange { str ->
                mViewModel.getFilteredRestaurantsList(str).observe(this@HomeFragment, Observer {
                    adapter.setNewItems(it as List<RestaurantWithMenu>)
                })
        }
    }


    private fun initWork() {
        Util.updateStatusBarColor(R.color.dark_red, requireActivity())
        (activity as HomeActivity).setTitle(R.string.home_title.get())
        setupRecycler()
    }



    private fun setupViewModel() {
        mViewModel = ViewModelProvider(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(HomeViewModel::class.java)
    }


}
