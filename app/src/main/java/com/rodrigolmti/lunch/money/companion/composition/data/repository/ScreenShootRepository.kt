package com.rodrigolmti.lunch.money.companion.composition.data.repository

import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.TokenDTO
import com.rodrigolmti.lunch.money.companion.composition.data.model.dto.UpdateTransactionDTO
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetSource
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetStatus
import com.rodrigolmti.lunch.money.companion.composition.domain.model.AssetType
import com.rodrigolmti.lunch.money.companion.composition.domain.model.BudgetModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.CategoryConfigModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.CategoryModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.RecurringModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionCategoryModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionModel
import com.rodrigolmti.lunch.money.companion.composition.domain.model.TransactionStatus
import com.rodrigolmti.lunch.money.companion.composition.domain.model.UserModel
import com.rodrigolmti.lunch.money.companion.composition.domain.repository.ILunchRepository
import com.rodrigolmti.lunch.money.companion.core.LunchError
import com.rodrigolmti.lunch.money.companion.core.Outcome
import com.rodrigolmti.lunch.money.companion.core.cache.ICacheManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

private const val CATEGORIES_CACHE = "categories_cache"
private const val ASSET_CACHE = "asset_cache"

/**
 * This is a fake repository that will be used to generate screenshots.
 * It will be replaced by a real repository when the app is running.
 */
internal class ScreenShootRepository(
    cacheManager: ICacheManager,
) : ILunchRepository {

    private val _tickFlow = MutableSharedFlow<Unit>(replay = 0)
    override val transactionUpdateFlow: SharedFlow<Unit> = _tickFlow

    private val categoriesCache =
        cacheManager.createCache<String, List<TransactionCategoryModel>>(CATEGORIES_CACHE)
    private val assetCache = cacheManager.createCache<String, List<AssetModel>>(ASSET_CACHE)

    override suspend fun authenticateUser(tokenDTO: TokenDTO): Outcome<Unit, LunchError> {
        return Outcome.success(Unit)
    }

    override suspend fun logoutUser(): Outcome<Unit, LunchError> {
        return Outcome.success(Unit)
    }

    override suspend fun getTransactions(
        start: String,
        end: String
    ): Outcome<List<TransactionModel>, LunchError> {
        return Outcome.success(
            listOf(
                TransactionModel(
                    id = 1,
                    amount = 12.5f,
                    date = "2021-01-01",
                    isIncome = false,
                    payee = "McDonalds",
                    currency = "CAD",
                    status = TransactionStatus.CLEARED,
                    type = "Expense",
                    excludeFromTotals = false,
                    plaidAccountId = null,
                    asset = null,
                    notes = null,
                    toBase = 0.0,
                    category = TransactionCategoryModel(
                        id = 1,
                        name = "Food",
                        isIncome = false,
                        excludeFromTotals = false,
                        excludeFromBudget = false,
                        description = "Food",
                    ),
                    subtype = null,
                    originalName = null,
                    recurringId = null,
                    metadata = null,
                ),
                TransactionModel(
                    id = 2,
                    amount = 32.7f,
                    date = "2021-01-02",
                    isIncome = false,
                    payee = "Burrito Boyz",
                    currency = "CAD",
                    status = TransactionStatus.PENDING,
                    type = "Expense",
                    excludeFromTotals = false,
                    plaidAccountId = null,
                    asset = null,
                    notes = "Get together with friends",
                    toBase = 0.0,
                    category = TransactionCategoryModel(
                        id = 1,
                        name = "Food",
                        isIncome = false,
                        excludeFromTotals = false,
                        excludeFromBudget = false,
                        description = "Food",
                    ),
                    subtype = null,
                    originalName = "Burritzing card statement",
                    recurringId = null,
                    metadata = null,
                ),
                TransactionModel(
                    id = 3,
                    amount = -1000f,
                    date = "2021-01-03",
                    isIncome = true,
                    payee = "Company",
                    currency = "CAD",
                    status = TransactionStatus.CLEARED,
                    type = "Income",
                    excludeFromTotals = false,
                    plaidAccountId = null,
                    asset = null,
                    notes = null,
                    toBase = 0.0,
                    category = TransactionCategoryModel(
                        id = 2,
                        name = "Salary",
                        isIncome = true,
                        excludeFromTotals = false,
                        excludeFromBudget = false,
                        description = "Salary",
                    ),
                    subtype = null,
                    originalName = null,
                    recurringId = null,
                    metadata = null,
                ),
            )
        )
    }

    override suspend fun getRecurring(): Outcome<List<RecurringModel>, LunchError> {
        return Outcome.success(
            listOf(
                RecurringModel(
                    description = "Rent",
                    id = 1,
                    originalName = null,
                    currency = "CAD",
                    payee = "Landlord",
                    amount = 1000f,
                    cadence = "Monthly",
                    endDate = null,
                    billingDate = "2021-01-01",
                ),
                RecurringModel(
                    description = "Salary",
                    id = 2,
                    originalName = null,
                    currency = "CAD",
                    payee = "Company",
                    amount = -1000f,
                    cadence = "Monthly",
                    endDate = null,
                    billingDate = "2021-01-01",
                ),
                RecurringModel(
                    description = "Car Loan",
                    id = 3,
                    originalName = null,
                    currency = "CAD",
                    payee = "RBC",
                    amount = 500f,
                    cadence = "Monthly",
                    endDate = null,
                    billingDate = "2021-01-01",
                ),
                RecurringModel(
                    description = "Groceries",
                    id = 4,
                    originalName = null,
                    currency = "CAD",
                    payee = "Grocery Store",
                    amount = 200f,
                    cadence = "Weekly",
                    endDate = null,
                    billingDate = "2021-01-01",
                ),
            )
        )
    }

    override suspend fun getTransaction(id: Long): Outcome<TransactionModel, LunchError> {
        return Outcome.success(
            TransactionModel(
                id = 1,
                amount = 100f,
                date = "2021-01-01",
                isIncome = false,
                payee = "McDonalds",
                currency = "CAD",
                status = TransactionStatus.CLEARED,
                type = "Expense",
                excludeFromTotals = false,
                plaidAccountId = null,
                asset = null,
                notes = null,
                toBase = 0.0,
                category = TransactionCategoryModel(
                    id = 1,
                    name = "Food",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Food",
                ),
                subtype = null,
                originalName = null,
                recurringId = null,
                metadata = null,
            ),
        )
    }

    override suspend fun updateTransaction(dto: UpdateTransactionDTO): Outcome<Unit, LunchError> {
        return Outcome.success(
            Unit
        )
    }

    override suspend fun getBudgets(
        start: String,
        end: String
    ): Outcome<List<BudgetModel>, LunchError> {
        return Outcome.success(
            listOf(
                BudgetModel(
                    categoryName = "Food",
                    categoryId = 1,
                    categoryGroupName = "Food",
                    groupId = 1,
                    isGroup = false,
                    isIncome = false,
                    excludeFromBudget = false,
                    excludeFromTotals = false,
                    recurring = emptyList(),
                    config = null,
                    order = 1,
                    data = listOf(
                        CategoryModel(
                            numTransactions = 2,
                            spendingToBase = 45.0f,
                            budgetToBase = 100.0f,
                            budgetAmount = 100.0f,
                            budgetCurrency = "CAD",
                            isAutomated = false,
                            date = "2021-01-01",
                        )
                    )
                ),
                BudgetModel(
                    categoryName = "Salary",
                    categoryId = 2,
                    categoryGroupName = "Salary",
                    groupId = 2,
                    isGroup = false,
                    isIncome = true,
                    excludeFromBudget = false,
                    excludeFromTotals = false,
                    recurring = emptyList(),
                    config = CategoryConfigModel(
                        currency = "CAD",
                        amount = 10000f,
                        cadence = "Monthly",
                        toBase = 0.0f,
                        configId = 1,
                        autoSuggest = "yes",
                    ),
                    order = 2,
                    data = listOf(
                        CategoryModel(
                            numTransactions = 1,
                            spendingToBase = 0.0f,
                            budgetToBase = 0.0f,
                            budgetAmount = 0.0f,
                            budgetCurrency = "CAD",
                            isAutomated = false,
                            date = "2021-01-01",
                        )
                    )
                ),
                BudgetModel(
                    categoryName = "Car",
                    categoryId = 3,
                    categoryGroupName = "Car",
                    groupId = 3,
                    isGroup = false,
                    isIncome = false,
                    excludeFromBudget = false,
                    excludeFromTotals = false,
                    recurring = emptyList(),
                    config = null,
                    order = 3,
                    data = listOf(
                        CategoryModel(
                            numTransactions = 0,
                            spendingToBase = 0.0f,
                            budgetToBase = 220.0f,
                            budgetAmount = 250.0f,
                            budgetCurrency = "CAD",
                            isAutomated = false,
                            date = "2021-01-01",
                        )
                    )
                ),
                BudgetModel(
                    categoryName = "Rent",
                    categoryId = 4,
                    categoryGroupName = "Rent",
                    groupId = 4,
                    isGroup = false,
                    isIncome = false,
                    excludeFromBudget = false,
                    excludeFromTotals = false,
                    recurring = emptyList(),
                    config = null,
                    order = 4,
                    data = listOf()
                ),
                BudgetModel(
                    categoryName = "Groceries",
                    categoryId = 5,
                    categoryGroupName = "Groceries",
                    groupId = 5,
                    isGroup = false,
                    isIncome = false,
                    excludeFromBudget = false,
                    excludeFromTotals = false,
                    recurring = emptyList(),
                    config = CategoryConfigModel(
                        currency = "CAD",
                        amount = 200f,
                        cadence = "Weekly",
                        toBase = 0.0f,
                        configId = 2,
                        autoSuggest = "yes",
                    ),
                    order = 5,
                    data = listOf(
                        CategoryModel(
                            numTransactions = 4,
                            spendingToBase = 282.0f,
                            budgetToBase = 600.0f,
                            budgetAmount = 600.0f,
                            budgetCurrency = "CAD",
                            isAutomated = false,
                            date = "2021-01-01",
                        )
                    )
                ),
                BudgetModel(
                    categoryName = "Entertainment",
                    categoryId = 6,
                    categoryGroupName = "Entertainment",
                    groupId = 6,
                    isGroup = false,
                    isIncome = false,
                    excludeFromBudget = false,
                    excludeFromTotals = false,
                    recurring = emptyList(),
                    config = null,
                    order = 6,
                    data = listOf()
                ),
                BudgetModel(
                    categoryName = "Other",
                    categoryId = 7,
                    categoryGroupName = "Other",
                    groupId = 7,
                    isGroup = false,
                    isIncome = false,
                    excludeFromBudget = false,
                    excludeFromTotals = false,
                    recurring = emptyList(),
                    config = null,
                    order = 7,
                    data = listOf()
                ),
            )
        )
    }

    override fun getAssets(): List<AssetModel> = assetCache.get(ASSET_CACHE, emptyList())

    override suspend fun cacheTransactionCategories() {
        categoriesCache.put(
            CATEGORIES_CACHE,
            listOf(
                TransactionCategoryModel(
                    id = 1,
                    name = "Food",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Food",
                ),
                TransactionCategoryModel(
                    id = 2,
                    name = "Salary",
                    isIncome = true,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Salary",
                ),
                TransactionCategoryModel(
                    id = 3,
                    name = "Car",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Car",
                ),
                TransactionCategoryModel(
                    id = 4,
                    name = "Rent",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Rent",
                ),
                TransactionCategoryModel(
                    id = 5,
                    name = "Groceries",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Groceries",
                ),
                TransactionCategoryModel(
                    id = 6,
                    name = "Entertainment",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Entertainment",
                ),
                TransactionCategoryModel(
                    id = 7,
                    name = "Other",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Other",
                ),
                TransactionCategoryModel(
                    id = 8,
                    name = "Other Income",
                    isIncome = true,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Other Income",
                ),
                TransactionCategoryModel(
                    id = 9,
                    name = "Other Expense",
                    isIncome = false,
                    excludeFromTotals = false,
                    excludeFromBudget = false,
                    description = "Other Expense",
                ),
            )
        )
    }

    override suspend fun cacheAssets() {
        assetCache.put(
            ASSET_CACHE, listOf(
                AssetModel(
                    currency = "CAD",
                    id = 1,
                    type = AssetType.DEPOSITORY,
                    balance = 4523.0,
                    source = AssetSource.SYNCED,
                    name = "TD  Smart Account",
                    status = AssetStatus.ACTIVE,
                    balanceAsOf = "2021-01-01",
                    subtypeName = "Checking",
                    institutionName = "TD",
                ),
                AssetModel(
                    currency = "CAD",
                    id = 2,
                    type = AssetType.LOAN,
                    balance = 26345.0,
                    source = AssetSource.MANUAL,
                    name = "RBC Car Loan",
                    status = AssetStatus.ACTIVE,
                    balanceAsOf = "2021-01-01",
                    subtypeName = "Loan",
                    institutionName = "RBC",
                ),
                AssetModel(
                    currency = "CAD",
                    id = 3,
                    type = AssetType.CREDIT,
                    balance = 278.0,
                    source = AssetSource.MANUAL,
                    name = "RBC Credit Card",
                    status = AssetStatus.ACTIVE,
                    balanceAsOf = "2021-01-01",
                    subtypeName = "Credit Card",
                    institutionName = "RBC",
                ),
            )
        )
    }

    override fun getSessionUser(): UserModel {
        return UserModel(
            userName = "Rodrigo Martins",
            id = 1,
            email = "rodrigo@gmail.com",
            accountId = 1,
            budgetName = "Personal",
            apiKeyLabel = "Android"
        )
    }

    override fun getSessionToken(): TokenDTO {
        return TokenDTO("token")
    }

    override fun updatePrimaryCurrency(currency: String) {
        // no-op
    }

    override fun getPrimaryCurrency(): String {
        return "CAD"
    }

    override fun getCategories(): List<TransactionCategoryModel> {
        return categoriesCache.get(CATEGORIES_CACHE, emptyList())
    }
}