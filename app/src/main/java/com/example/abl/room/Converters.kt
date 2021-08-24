package com.example.abl.room

import androidx.room.TypeConverter
import com.example.abl.model.GetPreviousVisit
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<GetPreviousVisit>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<GetPreviousVisit>::class.java).toList()

}