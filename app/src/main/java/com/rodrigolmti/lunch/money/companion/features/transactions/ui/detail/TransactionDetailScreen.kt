@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigolmti.lunch.money.companion.R
import com.rodrigolmti.lunch.money.companion.core.utils.LunchMoneyPreview
import com.rodrigolmti.lunch.money.companion.core.utils.formatCurrency
import com.rodrigolmti.lunch.money.companion.core.utils.toDate
import com.rodrigolmti.lunch.money.companion.core.utils.toFormattedDate
import com.rodrigolmti.lunch.money.companion.features.transactions.model.TransactionDetailView
import com.rodrigolmti.lunch.money.companion.features.transactions.model.UpdateTransactionView
import com.rodrigolmti.lunch.money.companion.uikit.components.Center
import com.rodrigolmti.lunch.money.companion.uikit.components.EmptyState
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchAppBar
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchButton
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchDropDown
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchLoading
import com.rodrigolmti.lunch.money.companion.uikit.components.LunchTextField
import com.rodrigolmti.lunch.money.companion.uikit.components.VerticalSpacer
import com.rodrigolmti.lunch.money.companion.uikit.modal.BottomSheetComponent
import com.rodrigolmti.lunch.money.companion.uikit.modal.LunchDatePicker
import com.rodrigolmti.lunch.money.companion.uikit.theme.CompanionTheme
import com.rodrigolmti.lunch.money.companion.uikit.theme.MidnightSlate
import com.rodrigolmti.lunch.money.companion.uikit.theme.SilverLining
import com.rodrigolmti.lunch.money.companion.uikit.theme.White
import kotlinx.coroutines.launch
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun TransactionsDetailScreen(
    uiModel: ITransactionDetailUIModel = DummyITransactionDetailUIModel(),
    id: Long = 0,
    onBackClick: () -> Unit = {},
) {
    val viewState by uiModel.viewState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true },
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    val errorState = remember {
        mutableStateOf<TransactionsDetailErrorState>(TransactionsDetailErrorState.Idle)
    }

    LaunchedEffect(Unit) {
        uiModel.getTransaction(id)
    }

    when (viewState) {
        is TransactionDetailUiState.Error -> {
            LaunchedEffect(Unit) {
                errorState.value = TransactionsDetailErrorState.GetTransactionError
                scope.launch { sheetState.show() }
            }
        }

        is TransactionDetailUiState.Loading -> {
            // no-op
        }

        is TransactionDetailUiState.Success -> {
            val state = (viewState as TransactionDetailUiState.Success)

            if (state.updated) {
                val message = stringResource(R.string.transaction_updated_label)

                LaunchedEffect(Unit) {
                    scope.launch {
                        snackBarHostState.showSnackbar(message)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            LunchAppBar(
                stringResource(R.string.transaction_title),
                onBackClick = onBackClick,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    it,
                    containerColor = MidnightSlate,
                    contentColor = White,
                )
            }
        },
    ) {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                BottomSheetComponent(
                    stringResource(R.string.transaction_modal_error_title),
                    stringResource(R.string.transaction_modal_error_message),
                ) {
                    scope.launch { sheetState.hide() }
                }
            },
            modifier = Modifier.fillMaxSize(),
            sheetBackgroundColor = MidnightSlate,
            sheetShape = MaterialTheme.shapes.medium,
        ) {
            when (viewState) {
                is TransactionDetailUiState.Loading -> {
                    Center {
                        LunchLoading()
                    }
                }

                is TransactionDetailUiState.Error -> {
                    EmptyState(
                        stringResource(R.string.transaction_error_message),
                    )
                }

                is TransactionDetailUiState.Success -> {
                    val transaction = (viewState as TransactionDetailUiState.Success).transaction

                    BuildSuccessState(transaction) {
                        uiModel.updateTransaction(it, transaction)
                    }
                }
            }
        }
    }
}

@Composable
private fun BuildSuccessState(
    view: TransactionDetailView,
    onUpdate: (UpdateTransactionView) -> Unit = {},
) {
    val transaction = view.transaction

    val calendar = Calendar.getInstance()
    transaction.date.toDate()?.let {
        calendar.time = it
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )

    var datePickerVisible by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    var category by remember { mutableStateOf(transaction.category) }
    var date by remember { mutableLongStateOf(calendar.timeInMillis) }
    var payee by remember { mutableStateOf(transaction.payee) }
    var notes by remember { mutableStateOf(transaction.notes) }

    fun getEditState() =
        "${category?.hashCode()}${date.hashCode()}${payee.hashCode()}${notes.hashCode()}"

    val editState = remember { mutableStateOf(getEditState()) }

    if (datePickerVisible) {
        LunchDatePicker(
            datePickerState = datePickerState,
            onDateSelected = {
                date = datePickerState.selectedDateMillis ?: 0L
            },
            onDismissRequest = {
                datePickerVisible = false
            }
        )
    }

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
        LunchDropDown(
            modifier = Modifier
                .fillMaxWidth(),
            selectedOption = category,
            onOptionSelected = {
                category = it
            },
            options = view.categories,
            label = stringResource(R.string.transaction_category_label),
            expanded = expanded,
            getSelectedLabel = {
                it?.name ?: ""
            },
            onExpandedChange = {
                expanded = it
            }
        )
        LunchTextField(
            label = stringResource(R.string.transaction_date_label),
            text = date.toFormattedDate(),
            enabled = false,
            disabledTextColor = White,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))

                .clickable {
                    datePickerVisible = true
                }
        )
        LunchTextField(
            label = stringResource(R.string.transaction_payee_label),
            text = payee,
            onValueChange = {
                payee = it
            },
        )
        Text(
            text = stringResource(R.string.transaction_amount_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = formatCurrency(
                transaction.amount,
                transaction.currency
            ),
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        LunchTextField(
            label = stringResource(R.string.transaction_notes_label),
            text = notes ?: "",
            maxLines = 3,
            onValueChange = {
                notes = it
            },
        )
        Text(
            text = stringResource(R.string.transaction_original_name_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = transaction.originalName ?: "",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )
        Text(
            text = stringResource(R.string.transaction_paid_from_label),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = SilverLining,
            style = CompanionTheme.typography.body,
        )
        Text(
            text = transaction.assetName ?: "",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = White,
            style = CompanionTheme.typography.bodyBold,
        )

        if (view.transaction.metadata != null) {
            TransactionMetadataComponent(view = view.transaction.metadata)
        }

        LunchButton(
            label = "Update",
            isLoading = false,
            isEnabled = editState.value != getEditState(),
        ) {
            onUpdate(
                UpdateTransactionView(
                    date = date.toFormattedDate(),
                    id = transaction.id,
                    payee = payee,
                    notes = notes,
                    category = category,
                )
            )
        }
        VerticalSpacer(height = CompanionTheme.spacings.spacingD)
    }
}

@Composable
@LunchMoneyPreview
private fun TransactionsScreenPreview(
    @PreviewParameter(TransactionDetailUIModelProvider::class) uiModel: ITransactionDetailUIModel
) {
    CompanionTheme {
        TransactionsDetailScreen(uiModel)
    }
}
