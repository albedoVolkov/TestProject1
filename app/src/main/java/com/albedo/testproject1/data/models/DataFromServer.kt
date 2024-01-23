package com.albedo.testproject1.data.models

import com.google.gson.annotations.SerializedName

data class DataFromServer (
    @SerializedName("services")
    var services : List<String>,
    @SerializedName("pins")
    var pins : List<PinDataUIState>,
)

data class PinDataUIState (
    @SerializedName("id")
    var id : Long,
    @SerializedName("service")
    var service : String,
    @SerializedName("coordinates")
    var coordinates : CoordinateDataUIState,
)

data class CoordinateDataUIState (
    @SerializedName("lat")
    var lat : Double,
    @SerializedName("lng")
    var lng : Double
)


