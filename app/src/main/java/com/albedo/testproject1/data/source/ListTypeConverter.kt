package com.albedo.testproject1.data.source

import androidx.room.TypeConverter
import com.albedo.testproject1.data.models.CoordinateUIState
import com.albedo.testproject1.data.models.PinUIState
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.data.models.fromStringToCoordinateItem
import com.albedo.testproject1.data.models.fromStringToPinItem
import com.albedo.testproject1.data.models.fromStringToServiceItem

class ListTypeConverter {
    @TypeConverter
    fun fromStringListToString(value: List<String>): String {
        return value.joinToString(separator = "||,||")
    }
    @TypeConverter
    fun fromStringToStringList(value: String): List<String> {

        if (value == "" || value == "[]" || value == "null"){
            return listOf()
        }
        return value.split("||,||").map { it }
    }



    @TypeConverter
    fun fromStringToIntList(value: String): List<Int> {
        if (value == ""){
            return listOf()
        }
        return value.split("||,||").map { it.toInt() }
    }
    @TypeConverter
    fun fromIntListToString(value: List<Int>): String {
        return value.joinToString(separator = "||,||")
    }




    @TypeConverter
    fun fromServiceUIStateListToString(value: List<ServiceUIState>): String {
        return value.joinToString(separator = "||,||")
    }
    @TypeConverter
    fun fromStringToServiceUIStateList(value: String): List<ServiceUIState> {
        if (value == "" || value == "[]" || value == "null"){
            return listOf()
        }
        return value.split("||,||").map { fromStringToServiceItem(it)!! }
    }




    @TypeConverter
    fun fromPinUIStateListToString(value: List<PinUIState>): String {
        return value.joinToString(separator = "||,||")
    }
    @TypeConverter
    fun fromStringToPinUIStateList(value: String): List<PinUIState> {
        if (value == "" || value == "[]" || value == "null"){
            return listOf()
        }else {
            return value.split("||,||").map {
                fromStringToPinItem(it)
                    ?: throw Exception("fromStringToPinItem gets not correct item")
            }
        }
    }



    @TypeConverter
    fun fromCoordinateUIStateListToString(value: List<CoordinateUIState>): String {
        return value.joinToString(separator = "||,||")
    }
    @TypeConverter
    fun fromStringToCoordinateUIStateList(value: String): List<CoordinateUIState> {
        if (value == "" || value == "[]" || value == "null"){
            return listOf()
        }else {
            return value.split("||,||").map {
                fromStringToCoordinateItem(it)
                    ?: throw Exception("fromStringToCoordinateUIStateList gets not correct item")
            }
        }
    }





    @TypeConverter
    fun fromServiceUIStateToString(value: ServiceUIState): String { return value.toString() }

    @TypeConverter
    fun fromStringToServiceUIState(value: String): ServiceUIState { return fromStringToServiceItem(value)!! }



    @TypeConverter
    fun fromPinUIStateToString(value: PinUIState): String { return value.toString() }

    @TypeConverter
    fun fromStringToPinUIState(value: String): PinUIState { return fromStringToPinItem(value)!! }



    @TypeConverter
    fun fromCoordinateUIStateToString(value: CoordinateUIState): String { return value.toString() }

    @TypeConverter
    fun fromStringToCoordinateUIState(value: String): CoordinateUIState { return fromStringToCoordinateItem(value)!! }

}