package com.albedo.testproject1.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.yandex.mapkit.geometry.Point

@Entity(tableName = "pins")
data class PinUIState (

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : Long,

    @ColumnInfo(name = "service")
    var service : String,

    @ColumnInfo(name = "coordinates")
    var coordinates : CoordinateUIState,
){
    override fun toString(): String {
        return Gson().toJson(this,PinUIState::class.java)
    }
}

fun fromStringToPinItem(string: String): PinUIState? {
    return try {
        Gson().fromJson(string, PinUIState::class.java)
    }catch(e : Exception){ null }
}

