package com.rodrigolmti.lunch.money.companion.core.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Repeatable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class LunchMoneyPreview