package com.albedo.testproject1.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.data.repository.services.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterActivityViewModel @Inject constructor(private val serviceRepository : ServiceRepository) : ViewModel(){

    private val TAG = "FilterActivityViewModel"


    //ITEMS
    private var _showItems : List<ServiceUIState> = emptyList()
    val showItems: List<ServiceUIState> get() = _showItems// this list is for showing and sorting

    private var _mainItems : List<ServiceUIState> = listOf()
    val mainItems: List<ServiceUIState> get() = _mainItems// this list isn't for showing and not sorted

    val data : Flow<List<ServiceUIState>> = serviceRepository.getItemListFlow().asLiveDataFlow()


    private var _selectedItemslist : MutableList<ServiceUIState> = mutableListOf()
    val selectedItemslist: List<ServiceUIState> get() = _selectedItemslist



    private fun <T> Flow<T>.asLiveDataFlow() = shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

    fun addInSelectedItemsList(item : ServiceUIState) {
        Log.d(TAG, "addInSelectedItemsList : item - $item")
        if(!selectedItemslist.contains(item)){
            _selectedItemslist.add(item)
        }else{
            Log.d(TAG, "addInSelectedItemsList : item - $item is already in the list")
        }
    }

    fun deleteFromSelectedItemsList(item : ServiceUIState) {
        if(selectedItemslist.contains(item)){
            _selectedItemslist.remove(item)
        }else{
            Log.d(TAG, "deleteFromSelectedItemsList : item - $item wasn't in the list")
        }
    }

    fun clearFromSelectedItemsList() {
        _selectedItemslist.clear()
    }

    fun setListItemsInViewModel(list : List<ServiceUIState>) {
        Log.d(TAG, "setListItemsInViewModel : list - $list")
        _mainItems = list
    }


    fun filterItems(filterType: String) {
        _showItems = when (filterType) {
            "None" -> emptyList()
            "All" -> mainItems
            "Reversed" -> mainItems.reversed()
            else -> mainItems
        }
    }


    fun refreshData(){
        viewModelScope.launch {
            async {serviceRepository.refreshItemsData()}.await()
        }
    }

}
