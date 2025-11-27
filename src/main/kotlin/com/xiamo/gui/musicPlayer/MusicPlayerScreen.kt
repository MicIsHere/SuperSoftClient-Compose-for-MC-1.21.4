package com.xiamo.gui.musicPlayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SecureTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sun.jndi.toolkit.dir.DirSearch.search
import com.xiamo.SuperSoft
import com.xiamo.gui.ComposeScreen
import com.xiamo.module.ModuleManager
import com.xiamo.module.modules.render.ClickGui.instance
import com.xiamo.utils.misc.MediaPlayer
import kotlinx.coroutines.delay
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.Icons
import net.minecraft.text.Text
import java.io.File
import java.io.FileInputStream
import java.lang.reflect.Constructor

class MusicPlayerScreen(var parentScreen : Screen? = null) : ComposeScreen(Text.of("MusicPlayer")) {



    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @Preview
    @Composable
    override fun renderCompose() {
        val density = LocalDensity.current

        val backgroundColor = Color(34,11,28)

        val width = with(density) {400.dp }

        val height = with(density) {250.dp }

        val buttonWidth = with(density) {90.dp }

        val buttonHeight = with(density) {20.dp }

        val leftButtonModifier = Modifier.width(width = buttonWidth).height(buttonHeight)
        val leftButtonShape = RoundedCornerShape(20.dp)
        val leftButtonColors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = Color.White)

        var searchVisible = remember { mutableStateOf(false) }



        var page = mutableStateOf("Search")

        LaunchedEffect(Unit) {
            isVisible = true
        }
        LaunchedEffect(isVisible){
            if (!isVisible){
                delay(300)
                MinecraftClient.getInstance().execute {
                    MinecraftClient.getInstance().setScreen(null)
                    MinecraftClient.getInstance().overlay = null
                }





            }
        }

        AnimatedVisibility(
            isVisible,
            enter = expandIn(),
            exit = shrinkOut(tween(durationMillis = 300))
        ) {
            MaterialTheme {
                Box(modifier = Modifier.Companion.fillMaxSize(), contentAlignment = Alignment.Companion.Center) {

                    Column(modifier = Modifier
                        .width(width)
                        .height(height)
                        .background(backgroundColor,RoundedCornerShape(5))

                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(height-25.dp)
                            ,
                             horizontalArrangement = Arrangement.Center
                        ) {
                            Column(modifier = Modifier
                                .width(width/4)
                                .fillMaxHeight()
                                .background(Color.Black.copy(0.8f), shape = RoundedCornerShape(5))
                                .padding(top = 20.dp)
                                , horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Music Player", fontSize = 11.sp, color = Color.White)

                                Spacer(modifier = Modifier.size(30.dp))

                                Button(modifier = leftButtonModifier, onClick = {
                                    page.value = "Search"
                                }, shape = leftButtonShape, colors = leftButtonColors, contentPadding = PaddingValues(0.dp)){
                                    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center) {
                                        val bitmap =  SuperSoft.javaClass.getResourceAsStream("/assets/supersoft/ui/icon/search.png").readAllBytes().decodeToImageBitmap()
                                        Icon(bitmap = bitmap,contentDescription = "Search", tint = Color.White, modifier = Modifier.size(12.dp).padding(end = 5.dp))
                                        Text("搜索",fontSize = 5.sp,color = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.size(3.dp))

                                Button(modifier = leftButtonModifier,onClick = {
                                    page.value = "List"

                                },shape = leftButtonShape,colors = leftButtonColors, contentPadding = PaddingValues(0.dp)){
                                    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center) {
                                        val bitmap = SuperSoft.javaClass.getResourceAsStream("/assets/supersoft/ui/icon/list.png").readAllBytes().decodeToImageBitmap()
                                        Icon(bitmap = bitmap,contentDescription = "List", tint = Color.White, modifier = Modifier.size(12.dp).padding(end = 5.dp))
                                        Text("列表",fontSize = 5.sp,color = Color.White)
                                    }
                                }

                            }

                            Spacer(modifier = Modifier.fillMaxHeight().width(2.dp).shadow(5.dp, spotColor = Color.White.copy(0.1f)))

                            Column(modifier=Modifier.fillMaxSize()){
                                when (page.value) {

                                    "Search" -> searchPage()

                                    "List" ->{

                                    }
                                }




                            }

                        }



                        Row(modifier = Modifier
                            .fillMaxSize()
                            .background(Color(224,224,224,180)
                                ,RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 12.dp))
                            , verticalAlignment = Alignment.CenterVertically)
                        {


                        }



                    }


                }
            }
        }



        super.renderCompose()
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    width: Int = 200,
    height: Int = 20,
) {
    var isHovered by remember { mutableStateOf(false) }


    val borderAlpha by animateFloatAsState(when{
        isHovered -> 1f
        !isHovered -> 0.3f

        else -> {0.3f}
    },tween(200))

    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .background(Color(50, 20, 40), RoundedCornerShape(4.dp))
            .border(1.dp, Color.White.copy(alpha = borderAlpha), RoundedCornerShape(4.dp))
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(4.dp))
        ,
        contentAlignment = Alignment.CenterStart,

    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 8.sp,
            ),
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .fillMaxWidth()
                .onPointerEvent(PointerEventType.Enter){isHovered = true}
                .onPointerEvent(PointerEventType.Exit){isHovered = false}
                .onFocusChanged { isHovered = it.isFocused }

        )

        if (value.isEmpty()) {
            Text(
                "请输入歌曲名…",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 8.sp,
                modifier = Modifier.padding(horizontal = 6.dp).offset(y = -2.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun searchPage() {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit){
        visible = true
    }
    var songName by remember { mutableStateOf("")}

    AnimatedVisibility(visible,enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn()) {
        Row(horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(top = 15.dp).fillMaxWidth()) {
            SearchTextField(songName,onValueChange = {songName = it })
            Button(onClick = {
                println(songName)
            }, modifier = Modifier
                .shadow(50.dp, shape = RoundedCornerShape(5.dp))
                .padding(start = 10.dp)
                .width(30.dp)
                .height(20.dp)

                ,contentPadding = PaddingValues(0.dp),colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black.copy(alpha = 0.5f),contentColor = Color.White,)){
                Icon(SuperSoft.javaClass.getResourceAsStream("/assets/supersoft/ui/icon/search.png").readAllBytes().decodeToImageBitmap(), contentDescription = "Serach", tint = Color.White,modifier = Modifier.size(12.dp))
            }
        }
    }
}



