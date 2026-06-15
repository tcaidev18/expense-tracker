package com.aitc.expensetrackerandroid.navigation

import androidx.lifecycle.SavedStateHandle

fun SavedStateHandle.requireLongArg(key: String): Long =
    readLongArg(key) ?: error("Missing or invalid navigation argument: $key")

fun SavedStateHandle.requireStringArg(key: String): String =
    readStringArg(key) ?: error("Missing navigation argument: $key")

fun SavedStateHandle.requireIntArg(key: String): Int =
    readIntArg(key) ?: error("Missing or invalid navigation argument: $key")

private fun SavedStateHandle.readLongArg(key: String): Long? =
    when (val value = get<Any?>(key)) {
        is Long -> value
        is Int -> value.toLong()
        is String -> value.toLongOrNull()
        else -> null
    }

private fun SavedStateHandle.readIntArg(key: String): Int? =
    when (val value = get<Any?>(key)) {
        is Int -> value
        is Long -> value.toInt()
        is String -> value.toIntOrNull()
        else -> null
    }

private fun SavedStateHandle.readStringArg(key: String): String? =
    when (val value = get<Any?>(key)) {
        is String -> value.takeIf { it.isNotEmpty() }
        else -> value?.toString()?.takeIf { it.isNotEmpty() }
    }
