package com.rodrigolmti.lunch.money.uikit.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.R

@Composable
fun BouncingImageAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val bounce by infiniteTransition.animateFloat(
        label = "bounce",
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0.0f at 0 with LinearOutSlowInEasing
                1.0f at 1000 with LinearOutSlowInEasing
                0.0f at 2000 with LinearOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart
        )
    )

    val shadowScale = 1f - bounce

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(80.dp)
    ) {
        val density = LocalDensity.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_coin),
                contentDescription = "Bouncing Image",
                modifier = Modifier
                    .graphicsLayer {
                        translationY = with(density) { -18.dp.toPx() * bounce }
                    }
            )

            VerticalSpacer(height = 8.dp)

            Canvas(
                modifier = Modifier
                    .size(40.dp)
            ) {
                val shadowWidth = size.width
                val shadowHeight = size.height * shadowScale
                drawOval(
                    color = Color.Black,
                    size = androidx.compose.ui.geometry.Size(
                        width = shadowWidth,
                        height = shadowHeight
                    )
                )
            }
        }
    }
}