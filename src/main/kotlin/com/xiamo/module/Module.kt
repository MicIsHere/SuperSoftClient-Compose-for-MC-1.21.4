package com.xiamo.module

import com.xiamo.setting.AbstractSetting
import net.minecraft.client.gui.DrawContext
import java.util.concurrent.CopyOnWriteArrayList


open class Module(val name : String,val description : String) {
    var enabled: Boolean = false
    var key : Int =  -1

    val settings = CopyOnWriteArrayList<AbstractSetting<*>>()


    open fun onRender(drawContext: DrawContext){}

    open fun onTick(){}

    open fun onMouseClicked(mouseX: Int, mouseY : Int){}
    open fun onMouseReleased(mouseX: Int, mouseY : Int){}







}