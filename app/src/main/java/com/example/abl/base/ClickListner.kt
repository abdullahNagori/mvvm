package com.example.abl.base

interface ClickListner {
    fun <T> onClick(data :T,createNested:Boolean = false)
}