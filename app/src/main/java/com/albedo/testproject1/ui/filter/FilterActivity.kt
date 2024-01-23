package com.albedo.testproject1.ui.filter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.databinding.ActivityFilterBinding
import com.albedo.testproject1.viewmodels.FilterActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class FilterActivity : AppCompatActivity() {


    private val TAG = "FilterActivity"


    private lateinit var binding: ActivityFilterBinding
    private val viewModel: FilterActivityViewModel by viewModels()

    private lateinit var adapter: ServiceAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        setAdapter()
        setListeners()

        viewModel.refreshData()

        viewModel.data.onEach{
            Log.d(TAG, "mainItems : $it")
            viewModel.setListItemsInViewModel(it)
            viewModel.filterItems("All")
            setItemsListInRecyclerView(viewModel.showItems)
        }.launchIn(viewModel.viewModelScope)

    }



    private fun <T> views(block : ActivityFilterBinding.() -> T): T? = binding.block()


    private fun setListeners() {
        binding.toolBarCategoriesFragment.viewToolbarMain.setOnClickListener {
            sendDataToMainActivity(null)
        }
    }


    private fun setAdapter() {
        views {
            adapter = ServiceAdapter(this@FilterActivity)
            recyclerViewActivityFilter.layoutManager = LinearLayoutManager(this@FilterActivity,LinearLayoutManager.VERTICAL, false)
            recyclerViewActivityFilter.adapter = adapter

            adapter.onClickListener = object : ServiceAdapter.OnClickListener {
                override fun onClick(itemModel: ServiceUIState) {
                    sendDataToMainActivity(itemModel)
                }
            }
        }
    }



    private fun setItemsListInRecyclerView(list: List<ServiceUIState>) {
        Log.d(TAG, "setItemsListInRecyclerView : Adapter - $list")
        adapter.setData(list)
        showLoading(list.isEmpty())
    }



    private fun sendDataToMainActivity(itemData: ServiceUIState?) {
        val intent = Intent()
        Log.d(TAG, "sendDataToMainActivity : itemData - ${itemData.toString()}")
        intent.putExtra(Intent.EXTRA_TEXT, itemData?.toString())
        setResult(RESULT_OK, intent)
        finish()
    }


    private fun showLoading(success : Boolean) {
        views {
            if (success) {
                loaderLayout.loaderBackground.visibility = View.VISIBLE
                recyclerViewActivityFilter.visibility = View.GONE
                loaderLayout.circularLoader.showAnimationBehavior
            } else {
                loaderLayout.loaderBackground.visibility = View.GONE
                recyclerViewActivityFilter.visibility = View.VISIBLE
            }
        }
    }




}