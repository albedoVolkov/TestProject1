package com.albedo.testproject1.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coordinates")
data class CoordinateUIState (

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id : String,

    @ColumnInfo(name = "lat")
    var lat : Double,

    @ColumnInfo(name = "lng")
    var lng : Double
){
    override fun toString(): String {
        return Gson().toJson(this,CoordinateUIState::class.java)
    }
}

fun fromStringToCoordinateItem(string: String): CoordinateUIState? {
    return try {
        Gson().fromJson(string, CoordinateUIState::class.java)
    }catch(e : Exception){ null }
}