package com.rodrigolmti.lunch.money.companion.composition.di

import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.AnalyzeFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.BudgetFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.HomeFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.RecurringFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.SettingsFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.bridge.adapter.TransactionFeatureAdapter
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.IAppRepository
import com.rodrigolmti.lunch.money.companion.composition.domain.usecase.ExecuteStartupLogicUseCase
import com.rodrigolmti.lunch.money.companion.features.analyze.AnalyzeViewModel
import com.rodrigolmti.lunch.money.companion.features.analyze.IAnalyzeViewModel
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.AuthenticationViewModel
import com.rodrigolmti.lunch.money.companion.features.authentication.ui.IAuthenticationViewModel
import com.rodrigolmti.lunch.money.companion.features.budget.BudgetViewModel
import com.rodrigolmti.lunch.money.companion.features.budget.IBudgetViewModel
import com.rodrigolmti.lunch.money.companion.features.budget.detail.BudgetDetailViewModel
import com.rodrigolmti.lunch.money.companion.features.budget.detail.IBudgetDetailViewModel
import com.rodrigolmti.lunch.money.companion.features.home.ui.HomeViewModel
import com.rodrigolmti.lunch.money.companion.features.home.ui.IHomeViewModel
import com.rodrigolmti.lunch.money.companion.features.recurring.IRecurringViewModel
import com.rodrigolmti.lunch.money.companion.features.recurring.RecurringViewModel
import com.rodrigolmti.lunch.money.companion.features.settings.ISettingsViewModel
import com.rodrigolmti.lunch.money.companion.features.settings.SettingsViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.ITransactionsViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.TransactionsViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail.ITransactionDetailViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.detail.TransactionDetailViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary.ITransactionsSummaryViewModel
import com.rodrigolmti.lunch.money.companion.features.transactions.ui.summary.TransactionsSummaryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val authenticationModule = module {
    viewModel<IAuthenticationViewModel> {
        AuthenticationViewModel(
            authenticateUser = { get<IAppRepository>().authenticateUser(TokenDTO(it)) },
            postAuthentication = { get<ExecuteStartupLogicUseCase>().invoke() },
        )
    }
}

private val analyzeModule = module {
    viewModel<IAnalyzeViewModel> {
        AnalyzeViewModel(
            getGroupTransaction = { start, end ->
                AnalyzeFeatureAdapter(get(), get()).getSumGroupedTransactions(start, end)
            },
        )
    }
}

private val homeModule = module {
    viewModel<IHomeViewModel> {
        HomeViewModel(
            getUserAccountOverview = { start, end ->
                HomeFeatureAdapter(get()).getAssetOverview(start, end)
            },
            refreshUserData = { get<ExecuteStartupLogicUseCase>().invoke() },
        )
    }
}

private val transactionsModule = module {
    viewModel<ITransactionsViewModel> {
        TransactionsViewModel(
            getUserTransactions = { start, end ->
                TransactionFeatureAdapter(get()).getTransactions(start, end)
            },
            listenForTransactionUpdate = {
                get<IAppRepository>().transactionUpdateFlow
            }
        )
    }
    viewModel<ITransactionDetailViewModel> {
        TransactionDetailViewModel(
            getUserTransactions = { id ->
                TransactionFeatureAdapter(get()).getTransaction(id)
            },
            updateUserTransaction = { model ->
                TransactionFeatureAdapter(get()).updateTransaction(model)
            }
        )
    }
    viewModel<ITransactionsSummaryViewModel> {
        TransactionsSummaryViewModel(
            getTransactionSummary = { start, end ->
                TransactionFeatureAdapter(get()).getTransactionSummary(start, end)
            },
        )
    }
}

private val recurringModel = module {
    viewModel<IRecurringViewModel> {
        RecurringViewModel(
            getRecurring = {
                RecurringFeatureAdapter(get()).getRecurring()
            },
        )
    }
}

private val budgetModule = module {
    viewModel<IBudgetViewModel> {
        BudgetViewModel(
            getBudgetLambda = { start, end ->
                BudgetFeatureAdapter(get()).getBudget(start, end)
            },
        )
    }
    viewModel<IBudgetDetailViewModel> {
        BudgetDetailViewModel(
            getBudgetLambda = { id ->
                BudgetFeatureAdapter(get()).getBudget(id)
            },
        )
    }

}

private val settingsModule = module {
    viewModel<ISettingsViewModel> {
        SettingsViewModel(
            logoutUserRunner = { get<IAppRepository>().logoutUser() },
            updateCurrency = { get<IAppRepository>().updatePrimaryCurrency(it) },
            getUserDataRunner = {
                SettingsFeatureAdapter(get()).getUserData()
            },
        )
    }
}

internal val featuresModule = module {
    includes(
        authenticationModule,
        analyzeModule,
        homeModule,
        recurringModel,
        transactionsModule,
        budgetModule,
        settingsModule
    )
}