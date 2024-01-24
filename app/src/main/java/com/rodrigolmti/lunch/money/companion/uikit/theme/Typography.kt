package com.rodrigolmti.lunch.money.companion.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rodrigolmti.lunch.money.companion.R

@Immutable
object CompanionTypography {
    private val inconsolataSansFamily = FontFamily(
        Font(R.font.light, FontWeight.Light),
        Font(R.font.extra_light, FontWeight.ExtraLight),
        Font(R.font.regular, FontWeight.Normal),
        Font(R.font.medium, FontWeight.Medium),
        Font(R.font.bold, FontWeight.Bold),
        Font(R.font.extra_bold, FontWeight.ExtraBold),
        Font(R.font.semi_bold, FontWeight.SemiBold),
    )

    val header = TextStyle(
        fontFamily = inconsolataSansFamily,
        fontWeight = FontWeight.W900,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    )

    val headerBold = header.copy(
        fontWeight = FontWeight.Bold
    )

    val headerMedium = header.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    )

    val headerSmall = header.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    )

    val body = TextStyle(
        fontFamily = inconsolataSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    val bodyBold = body.copy(
        fontWeight = FontWeight.Bold
    )

    val bodyMedium = body.copy(
        fontWeight = FontWeight.Medium
    )

    val bodySmall = body.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    )
}