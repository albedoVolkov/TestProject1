package com.albedo.testproject1.viewmodels

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albedo.testproject1.R
import com.albedo.testproject1.data.models.PinUIState
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.data.repository.pins.PinsRepository
import com.albedo.testproject1.ui.main.MainActivity
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val pinRepository : PinsRepository) : ViewModel() {

    private val TAG = "MainActivityViewModel"

    private var _listService: List<ServiceUIState> = listOf()
    val listService: List<ServiceUIState> get() = _listService

        //ITEMS
        private var _showItems: MutableStateFlow<List<PinUIState>> = MutableStateFlow(listOf())
        val showItems: MutableStateFlow<List<PinUIState>> get() = _showItems

        private var _mainItems : List<PinUIState> = listOf()
        val mainItems: List<PinUIState> get() = _mainItems// this list isn't for showing and not sorted

        val data : Flow<List<PinUIState>> = pinRepository.getItemListFlow().shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)



        init{
            refreshData()
        }


    fun setListService(list: List<ServiceUIState>) {
        Log.d(TAG, "setListService : list - $list")
        _listService = list
    }


        fun filterItems(filterType: String, list : List<PinUIState> = mainItems) {
            _mainItems = list
            Log.d(TAG, "filterItems : filterType - $filterType, list - $list")
            _showItems.value = when (filterType) {
                "None" -> emptyList()
                "All" -> mainItems
                else -> emptyList()
            }
        }

        fun filterItems(filterType: List<String>, list : List<PinUIState> = mainItems) {
            _mainItems = list
            Log.d(TAG, "filterItems : filterType - $filterType, list - $list")
            val mainList = mutableListOf<PinUIState>()
            for(item in mainItems){
                for(filter in filterType) {
                    if (item.service == filter) {
                        mainList.add(item)
                    }
                }
            }
            _showItems.value = mainList
        }



    fun addMarker(pin : PinUIState, mapMain : MapView, marker: Bitmap) {
        Log.d(MainActivity.TAG, "addMarker was called")
        Log.d(MainActivity.TAG, "addMarker point : ${Point(pin.coordinates.lat,pin.coordinates.lng)}")
        val placemark = mapMain.map.mapObjects.addPlacemark(
            Point(pin.coordinates.lat,pin.coordinates.lng),
            ImageProvider.fromBitmap(marker))
        placemark.isVisible = true

    }





    private fun refreshData(){
        Log.d(TAG, "refreshData was called")
            viewModelScope.launch {
                async {pinRepository.refreshItemsData()}.await()
            }
        }

    }
