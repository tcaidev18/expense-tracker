package com.aitc.expensetrackerandroid.navigation

sealed interface Route {
    val path: String

    data object Start : Route {
        override val path: String = "start"
    }
}
