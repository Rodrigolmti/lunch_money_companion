package com.rodrigolmti.lunch.money.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rodrigolmti.lunch.money.R

private val inconsolataSansFamily = FontFamily(
    Font(R.font.light, FontWeight.Light),
    Font(R.font.extra_light, FontWeight.ExtraLight),
    Font(R.font.regular, FontWeight.Normal),
    Font(R.font.medium, FontWeight.Medium),
    Font(R.font.bold, FontWeight.Bold),
    Font(R.font.extra_bold, FontWeight.ExtraBold),
    Font(R.font.semi_bold, FontWeight.SemiBold),
)

val Header = TextStyle(
    fontFamily = inconsolataSansFamily,
    fontWeight = FontWeight.W900,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp
)

val Body = TextStyle(
    fontFamily = inconsolataSansFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp
)

val BodyBold = Body.copy(
    fontWeight = FontWeight.Bold
)