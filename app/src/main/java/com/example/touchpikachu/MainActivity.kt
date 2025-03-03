package com.example.touchpikachu

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickerGame()
        }
    }
}

@Composable
fun ClickerGame() {
    var count by remember { mutableStateOf(0) }
    var isClickable by remember { mutableStateOf(true) }
    var currentImage by remember { mutableStateOf(R.drawable.pikachu) }
    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current
    val soundIds = listOf(
        R.raw.sound1, R.raw.sound2, R.raw.sound3
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Card(
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0x00000)),
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .padding(bottom = 0.dp)
                .shadow(0.dp)
        ){
            Text(
                text = "$count",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }

        Image(
            painter = painterResource(id = currentImage),
            contentDescription = "Pikachu",
            modifier = Modifier
                .size(280.dp)
                .clickable(enabled = isClickable) {
                    count++
                    isClickable = false
                    currentImage = R.drawable.pikachuclicked

                    if (Random.nextInt(100) < 5) {
                        val randomSound = soundIds.random()
                        val mediaPlayer = MediaPlayer.create(context, randomSound)
                        mediaPlayer.start()
                        mediaPlayer.setOnCompletionListener { it.release() }
                    }

                    scope.launch {
                        delay(3000)
                        isClickable = true
                        currentImage = R.drawable.pikachu
                    }
                }
        )
    }
}