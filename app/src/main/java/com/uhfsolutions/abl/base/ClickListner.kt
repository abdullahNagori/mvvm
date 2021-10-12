package com.uhfsolutions.abl.base

interface ClickListner {
    fun <T> onClick(data :T,createNested:Boolean = false)
}