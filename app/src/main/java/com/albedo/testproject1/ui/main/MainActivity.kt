package com.albedo.testproject1.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.albedo.testproject1.R
import com.albedo.testproject1.data.models.CoordinateUIState
import com.albedo.testproject1.data.models.PinUIState
import com.albedo.testproject1.data.models.fromStringToServiceItem
import com.albedo.testproject1.databinding.ActivityMainBinding
import com.albedo.testproject1.ui.filter.FilterActivity
import com.albedo.testproject1.viewmodels.MainActivityViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){


    companion object {
        const val TAG = "MainActivity"
        const val MAPKIT_API_KEY = "d0040f57-9c1d-42aa-97e2-a2a53e03136f"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    private var mapMain : MapView? = null
    private val startLocation = Point(55.7522, 37.6156)
    private var zoomValue: Float = 12.0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiKey(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate was call")
        setMapView()
        moveToStartLocation()
        setListeners()
        requireData()
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val returnString = result.data?.getStringExtra (Intent.EXTRA_TEXT) ?: ""
            val service =  fromStringToServiceItem(returnString)

            if(service != null) {
                Log.d(TAG, "onActivityResult : filterItems - ${service.name}")
                viewModel.setService(service)
            }else{ Log.d(TAG, "onActivityResult : service - null") }
        }
    }



    // Если Activity уничтожается например,  при нехватке памяти или при повороте экрана) - сохраняем информацию, что API-ключ уже был получен ранее
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
    }


    override fun onStart() {
        mapMain?.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        mapMain?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapMain = null
    }

    //apiKey functions
    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey") ?: false // При первом запуске приложения всегда false
        if (!haveApiKey) {
            MapKitFactory.setApiKey(MAPKIT_API_KEY)
            MapKitFactory.initialize(this)
        }
    }


    private fun moveToStartLocation() {
        if(mapMain != null) {
            Log.d(TAG, "moveToStartLocation was called")
            mapMain!!.map.move(CameraPosition(startLocation, zoomValue, 0.0f, 0.0f))
            viewModel.addMarker(
                PinUIState(10000,"startLocation", CoordinateUIState("10000",startLocation.latitude,startLocation.longitude)),
                mapMain!!,
                createBitmapFromVector(R.drawable.baseline_location_on_24)!!
            )
        }
    }


    private fun requireData() {


        viewModel.data.onEach {
            Log.d(TAG, "mainItems : $it")
            if(viewModel.service != null){
                viewModel.filterItems(viewModel.service?.name!!, it)
            }else {
                viewModel.filterItems("NONE", it)
            }
        }.launchIn(viewModel.viewModelScope)


        viewModel.showItems.onEach {
            Log.d(TAG, "showItems : $it")
            for (item in it) {
               viewModel.addMarker(item,
                   mapMain!!,
                   createBitmapFromVector(R.drawable.baseline_location_on_24)!!
               )
            }
        }.launchIn(viewModel.viewModelScope)
    }




    //setters
    private fun setMapView() {
        mapMain = binding.map1ActivityMain
        if(mapMain?.map != null) {
            mapMain!!.map.isModelsEnabled = true
            mapMain!!.map.isIndoorEnabled = true
        }
    }

    private fun setListeners() {
        binding.textView1ActivityMain.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            resultLauncher.launch(intent)
        }
    }


    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(this, art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }



}