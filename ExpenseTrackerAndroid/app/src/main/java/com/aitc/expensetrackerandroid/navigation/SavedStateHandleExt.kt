package com.aitc.expensetrackerandroid.navigation

import androidx.lifecycle.SavedStateHandle

fun SavedStateHandle.requireLongArg(key: String): Long =
    get<Long>(key)
        ?: get<String>(key)?.toLongOrNull()
        ?: error("Missing or invalid navigation argument: $key")

fun SavedStateHandle.requireStringArg(key: String): String =
    get<String>(key)
        ?: get<Any?>(key)?.toString()?.takeIf { it.isNotEmpty() }
        ?: error("Missing navigation argument: $key")

fun SavedStateHandle.requireIntArg(key: String): Int =
    get<Int>(key)
        ?: get<Long>(key)?.toInt()
        ?: get<String>(key)?.toIntOrNull()
        ?: error("Missing or invalid navigation argument: $key")
