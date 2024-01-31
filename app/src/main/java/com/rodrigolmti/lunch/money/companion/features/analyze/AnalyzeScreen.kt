@file:OptIn(ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.analyze

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.modal.BottomSheetComponent
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SunburstGold
import kotlinx.coroutines.launch

@Composable
@LunchMoneyPreview
private fun TransactionsScreenPreview(
    @PreviewParameter(AnalyzeUIModelProvider::class) uiModel: IAnalyzeUIModel
) {
    CompanionTheme {
        AnalyzeScreen(uiModel)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedCrossfadeTargetStateParameter")
@Composable
internal fun AnalyzeScreen(
    uiModel: IAnalyzeUIModel = DummyIAnalyzeUIModel(),
    onBackClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    var selectedFilter by remember { mutableStateOf(AnalyzeFilterPreset.MONTH_TO_DATE) }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true },
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    val modalState = remember {
        mutableStateOf<AnalyzeBottomSheetState>(AnalyzeBottomSheetState.Idle)
    }

    when (viewState) {
        AnalyzeUIState.Error -> {
            LaunchedEffect(Unit) {
                modalState.value = AnalyzeBottomSheetState.GetGroupError
                scope.launch { sheetState.show() }
            }
        }

        AnalyzeUIState.Loading -> {}
        is AnalyzeUIState.Success -> {}
    }

    Scaffold(
        topBar = {
            LunchAppBar(
                "Analyze",
                onBackClick = onBackClick,
            ) {
                IconButton(onClick = {
                    modalState.value = AnalyzeBottomSheetState.FilterModal
                    scope.launch { sheetState.show() }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        modifier = Modifier.size(CompanionTheme.spacings.spacingE),
                        contentDescription = null,
                        tint = SunburstGold,
                    )
                }
            }
        }
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                when (modalState.value) {
                    AnalyzeBottomSheetState.FilterModal -> {
                        AnalyzeFilterBottomSheet(
                            selected = selectedFilter,
                            onFilterSelected = {
                                selectedFilter = it
                            },
                            onFilter = {
                                scope.launch { sheetState.hide() }
                                val (start, end) = selectedFilter.mapFilterPreset()
                                uiModel.getGroup(start, end)
                            }
                        )
                    }

                    AnalyzeBottomSheetState.GetGroupError -> {
                        BottomSheetComponent(
                            stringResource(R.string.transaction_modal_error_title),
                            stringResource(R.string.transaction_modal_error_message),
                        ) {
                            scope.launch { sheetState.hide() }
                        }
                    }

                    AnalyzeBottomSheetState.Idle -> {
                        // no-op
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {
            when (viewState) {
                AnalyzeUIState.Loading -> {
                    Center {
                        LunchLoading()
                    }
                }

                AnalyzeUIState.Error -> {
                    EmptyState(
                        stringResource(R.string.transaction_error_message),
                    )
                }

                is AnalyzeUIState.Success -> {

                    val map = (viewState as AnalyzeUIState.Success).group
                    val chartData = map.filter { it.value < 100 }.map { (key, value) ->
                        PieEntry(value, key)
                    }

                    Column(
                        modifier = Modifier
                            .padding(18.dp)
                            .size(320.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Crossfade(
                            targetState = chartData,
                            label = "Pie Chart"
                        ) {
                            AndroidView(factory = { context ->

                                PieChart(context).apply {

                                    layoutParams = LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                    )

                                    setUsePercentValues(true)
                                    description.isEnabled = false
                                    setExtraOffsets(5f, 10f, 5f, 5f)

                                    setDragDecelerationFrictionCoef(0.95f)


                                    isDrawHoleEnabled = true
                                    setHoleColor(Color(0xFFFFFFFF).toArgb())

                                    setTransparentCircleColor(Color(0xFFFFFFFF).toArgb())
                                    setTransparentCircleAlpha(110)

                                    holeRadius = 58f
                                    transparentCircleRadius = 61f

                                    setDrawCenterText(true)

                                    setRotationAngle(0f)
                                    // enable rotation of the chart by touch
                                    isRotationEnabled = true
                                    isHighlightPerTapEnabled = true

                                    // chart.setUnit(" €");
                                    // chart.setDrawUnitsInChart(true);

                                    animateY(1400, Easing.EaseInOutQuad)

                                    legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                                    legend.horizontalAlignment =
                                        Legend.LegendHorizontalAlignment.CENTER
                                    legend.orientation = Legend.LegendOrientation.VERTICAL
                                    legend.setDrawInside(false)
                                    legend.xEntrySpace = 7f
                                    legend.yEntrySpace = 0f
                                    legend.yOffset = 0f
                                    legend.isEnabled = true
                                    legend.textSize = 14F
                                }
                            }, modifier = Modifier
                                .wrapContentSize()
                                .padding(5.dp), update = {
                                updatePieChartWithData(it, chartData)
                            })
                        }

                    }

                }
            }
        }
    }
}

fun updatePieChartWithData(
    chart: PieChart,
    data: List<PieEntry>
) {

    val entries = ArrayList<PieEntry>()
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value, item.label ?: ""))
    }

    val ds = PieDataSet(entries, "")
    ds.colors = arrayListOf(
        Color(0xFF9D5834).toArgb(),
        Color(0xFFE5527E).toArgb(),
        Color(0xFF66A238).toArgb(),
        Color(0xFF2B8CBE).toArgb(),
        Color(0xFFE6612B).toArgb(),
        Color(0xFF3996A3).toArgb(),
        Color(0xFFDA3633).toArgb(),
        Color(0xFF56B869).toArgb(),
        Color(0xFFE34B30).toArgb(),
        Color(0xFF5088A6).toArgb(),
        Color(0xFFEBAA34).toArgb(),
        Color(0xFF4D6A8F).toArgb(),
        Color(0xFFD83767).toArgb(),
        Color(0xFF44857F).toArgb(),
        Color(0xFFE57B2F).toArgb(),
        Color(0xFF4283A0).toArgb(),
        Color(0xFFA63465).toArgb(),
        Color(0xFF5B8E64).toArgb(),
        Color(0xFFE24F87).toArgb(),
        Color(0xFF3F6974).toArgb(),
        Color(0xFFD69137).toArgb(),
        Color(0xFF72B890).toArgb(),
        Color(0xFFC64738).toArgb(),
        Color(0xFF7AABBE).toArgb(),
        Color(0xFFE06C31).toArgb(),
        Color(0xFF5576A5).toArgb(),
        Color(0xFFD34568).toArgb(),
        Color(0xFF5FAF78).toArgb(),
        Color(0xFFC44B8F).toArgb(),
        Color(0xFF4888A0).toArgb()
    )

    ds.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    ds.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    ds.sliceSpace = 2f
    ds.valueTextColor = Color(0xFFFFFFFF).toArgb()
    ds.valueTextSize = 16f
    ds.valueTypeface = Typeface.DEFAULT

    val d = PieData(ds)
    chart.data = d
    chart.setCenterTextSize(16f)
    chart.setCenterTextColor(Color(0xFFFFFFFF).toArgb())
    chart.highlightValue(null)
    chart.invalidate()
}