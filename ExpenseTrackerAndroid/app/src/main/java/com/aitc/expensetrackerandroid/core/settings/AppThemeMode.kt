package com.aitc.expensetrackerandroid.core.settings

enum class AppThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
    ;

    fun resolve(isSystemDark: Boolean): Boolean = when (this) {
        SYSTEM -> isSystemDark
        LIGHT -> false
        DARK -> true
    }

    companion object {
        fun fromStored(value: String?): AppThemeMode =
            entries.firstOrNull { it.name == value } ?: SYSTEM
    }
}
