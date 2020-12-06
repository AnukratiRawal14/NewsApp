package com.example.newsapp

object Colors{
    val colors = arrayOf("#3685BC","#D36280","#FA8056","#818BCA","#7D659F", "#51BAB3","4FB66C")
    var colorIndex = 1
    fun getColor():String{
        return colors[colorIndex++ % colors.size]
    }
}