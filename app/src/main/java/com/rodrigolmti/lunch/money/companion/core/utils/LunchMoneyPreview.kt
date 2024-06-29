package com.rodrigolmti.lunch.money.companion.core.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Repeatable
@Preview(
    name = "Dark mode",
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Pixel 4",
    device = Devices.PIXEL_7,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Tabled",
    device = Devices.TABLET,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class LunchMoneyPreview