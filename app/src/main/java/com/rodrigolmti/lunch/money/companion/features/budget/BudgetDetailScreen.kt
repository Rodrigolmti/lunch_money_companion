package com.rodrigolmti.lunch.money.companion.features.budget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.White

/**
 * Add a component to control which month the budget should be set to;
 * Add a list of recurring items related to this budget on a horizontal list;
 * Add a list of months that contains this budget on a horizontal list;
 *  With budget value and total spending;
 * Remove from the list of budgets the fields budget value and total spending;
 * Cache the budget item on the repository, so the detail screen have access to it;
 */

@Composable
fun BudgetDetailScreen(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var value by remember { mutableLongStateOf(0) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            LunchAppBar(
                title = "Budget Detail",
                onBackClick = onBackClick,
            )
        },
        modifier = modifier,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(
                    top = CompanionTheme.spacings.spacingJ,
                    start = CompanionTheme.spacings.spacingD,
                    end = CompanionTheme.spacings.spacingD,
                    bottom = CompanionTheme.spacings.spacingD,
                ),
            verticalArrangement = Arrangement.spacedBy(CompanionTheme.spacings.spacingD)
        ) {
            LunchTextField(
                label = "Budget Value",
                text = value.toString(),
                enabled = false,
                disabledTextColor = White,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {

                    }
            )
        }
    }
}

@Composable
@LunchMoneyPreview
private fun BudgetDetailScreenPreview() {
    CompanionTheme {
        BudgetDetailScreen()
    }
}