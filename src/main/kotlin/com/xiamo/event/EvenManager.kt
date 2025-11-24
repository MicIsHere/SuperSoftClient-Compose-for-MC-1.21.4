package com.xiamo.event

import com.xiamo.module.ModuleManager


object  EvenManager {


    init {
        eventBus.subscribe()
    }


    @EventTarget
    fun renderEvent(event: RenderEvent){
        ModuleManager.modules.filter { it.enabled }.forEach {
            it.onRender(event.drawContext)
        }
    }


    @EventTarget
    fun mouseClickedEvent(event: MouseClickedEvent){
        ModuleManager.modules.filter { it.enabled }.forEach {
            it.onMouseClicked(event.mouseX,event.mouseY)
        }

    }

    @EventTarget
    fun mouseReleaseEvent(event: MouseReleasedEvent){
        ModuleManager.modules.filter { it.enabled }.forEach {
            it.onMouseReleased(event.mouseX,event.mouseY)
        }

    }








}