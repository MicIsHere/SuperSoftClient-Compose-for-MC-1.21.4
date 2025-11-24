package com.xiamo.module.modules.render

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.xiamo.module.ComposeModule
import com.xiamo.setting.StringSetting

object Hud : ComposeModule("Hud","界面") {
    var title = StringSetting("Title","HUD标题","SuperSoft")


    init {
        this.enabled = true

        this.settings.add(title)
    }

    @Composable
    override fun renderCompose() {
        Box(Modifier.fillMaxSize()){
            Column {
                Text(title.value, fontSize = 150.sp)
                Text("Version 1.0 😍", fontSize = 50.sp)
            }
        }
    }
}