package com.aitc.expensetrackerandroid.navigation

sealed interface Route {
    val path: String

    data object ExpenseList : Route {
        override val path: String = "expense_list"
    }

    data object Splash : Route {
        override val path: String = "splash"
    }

    data class ExpenseDetail(
        val expenseId: Long,
    ) : Route {
        override val path: String = route(expenseId)

        companion object {
            const val path: String = "expense_detail/{expenseId}"
            const val ARG_EXPENSE_ID: String = "expenseId"

            fun route(expenseId: Long): String = "expense_detail/$expenseId"
        }
    }

    data object Welcome : Route {
        override val path: String = "welcome"
    }


    data object Debug : Route {
        override val path: String = "debug"
    }


    // SCAFFOLD:ROUTES
}
