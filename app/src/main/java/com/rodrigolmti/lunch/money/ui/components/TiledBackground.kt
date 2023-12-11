package com.rodrigolmti.lunch.money.ui.components

import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.R
import kotlin.math.ceil

@Composable
fun TiledBackgroundScreen() {
    AppCompatResources
        .getDrawable(LocalContext.current, R.drawable.ic_background)?.let { vectorDrawable ->
            val originalBitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(originalBitmap)
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            vectorDrawable.draw(canvas)

            val scaledBitmap = Bitmap.createScaledBitmap(
                originalBitmap,
                (originalBitmap.width * 0.4).toInt(),
                (originalBitmap.height * 0.4).toInt(),
                true
            )

            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val imageWidth = scaledBitmap.width / LocalDensity.current.density
                val imageHeight = scaledBitmap.height / LocalDensity.current.density

                val horizontalRepeats = ceil(constraints.maxWidth / imageWidth).toInt()
                val verticalRepeats = ceil(constraints.maxHeight / imageHeight).toInt()

                Canvas(modifier = Modifier.fillMaxSize()) {
                    for (x in 0 until horizontalRepeats) {
                        for (y in 0 until verticalRepeats) {
                            drawImage(
                                image = scaledBitmap.asImageBitmap(),
                                topLeft = Offset(
                                    x * imageWidth.dp.toPx(),
                                    y * imageHeight.dp.toPx()
                                )
                            )
                        }
                    }
                }
            }
        }
}
