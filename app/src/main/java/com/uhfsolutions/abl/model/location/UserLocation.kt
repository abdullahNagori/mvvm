package com.uhfsolutions.abl.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserLocation")
data class UserLocation (
    @ColumnInfo(name = "location_time")
    var location_time: String = (System.currentTimeMillis() / 1000).toString(),
    @ColumnInfo(name = "versionNo")
    var version_no: String = "1.0",
    @ColumnInfo(name = "batteryLevel")
    var battery_level: String = "70%",
    @ColumnInfo(name = "lat")
    var latitude: Double = 0.0,
    @ColumnInfo(name = "long")
    var longitude: Double = 0.0
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int = 0
}