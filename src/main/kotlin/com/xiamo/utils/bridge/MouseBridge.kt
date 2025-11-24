package com.xiamo.utils.bridge

interface MouseBridge {
    val xPos : Int


    val yPos : Int
}

object MousePosition : MouseBridge{
    override val xPos: Int
        get() = xPos
    override val yPos: Int
        get() = yPos
}


