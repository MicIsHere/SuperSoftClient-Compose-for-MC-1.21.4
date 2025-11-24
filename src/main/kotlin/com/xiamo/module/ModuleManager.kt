package com.xiamo.module

import com.xiamo.module.modules.render.Hud
import java.util.concurrent.CopyOnWriteArrayList

object ModuleManager {
    val modules = CopyOnWriteArrayList<Module>()



    init {
        modules.add(Hud)
    }







}