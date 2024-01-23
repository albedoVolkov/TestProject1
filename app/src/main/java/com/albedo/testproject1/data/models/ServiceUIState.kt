package com.albedo.testproject1.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(tableName = "services")
data class ServiceUIState(

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id : String,

    @SerializedName("number")
    @ColumnInfo(name = "number")
    var number : Long,

    @SerializedName("quantityPins")
    @ColumnInfo(name = "quantityPins")
    var quantityPins : Long,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name : String,
){
    override fun toString(): String {
        return Gson().toJson(this,ServiceUIState::class.java)
    }
}

fun fromStringToServiceItem(string: String): ServiceUIState? {
    return try {
        Gson().fromJson(string, ServiceUIState::class.java)
    }catch(e : Exception){
        null
    }
}

