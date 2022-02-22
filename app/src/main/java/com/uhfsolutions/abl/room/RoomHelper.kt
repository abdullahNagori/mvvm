package com.uhfsolutions.abl.room

import com.uhfsolutions.abl.repository.BaseRepository
import javax.inject.Inject

class RoomHelper @Inject constructor(private val daoAccess: DAOAccess, private val ablDatabase: ABLDatabase) : BaseRepository() {

}