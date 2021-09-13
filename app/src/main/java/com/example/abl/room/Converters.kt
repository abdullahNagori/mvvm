package com.example.abl.room

import androidx.room.TypeConverter
import com.example.abl.model.previousVisits.GetPreviousVisit
import com.example.abl.model.trainingAndQuiz.Material
import com.example.abl.model.trainingAndQuiz.Training
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<GetPreviousVisit>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<GetPreviousVisit>::class.java).toList()

    @TypeConverter
    fun listToJsonMaterial(value: List<Material>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToListMaterial(value: String) = Gson().fromJson(value, Array<Material>::class.java).toList()

    @TypeConverter
    fun listToJsonTraining(value: List<Training>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToListTraining(value: String) = Gson().fromJson(value, Array<Training>::class.java).toList()
}