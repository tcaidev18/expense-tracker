package com.aitc.expensetrackerandroid.ui.theme

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

data class DimenScale(
    val textScale: Float,
    val dimensScale: Float,
)

fun dimenScaleFor(widthSizeClass: WindowWidthSizeClass): DimenScale = when (widthSizeClass) {
    WindowWidthSizeClass.Compact -> DimenScale(textScale = 1.0f, dimensScale = 1.0f)
    WindowWidthSizeClass.Medium -> DimenScale(textScale = 1.08f, dimensScale = 1.12f)
    WindowWidthSizeClass.Expanded -> DimenScale(textScale = 1.15f, dimensScale = 1.2f)
    else -> DimenScale(textScale = 1.0f, dimensScale = 1.0f)
}
