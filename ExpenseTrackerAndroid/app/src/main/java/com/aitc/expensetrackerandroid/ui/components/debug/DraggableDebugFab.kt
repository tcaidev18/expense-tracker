package com.aitc.expensetrackerandroid.ui.components.debug

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.aitc.expensetrackerandroid.R
import kotlin.math.roundToInt

@Composable
fun DraggableDebugFab(
    onClick: () -> Unit,
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    if (!visible) return

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .zIndex(1f),
    ) {
        val density = LocalDensity.current
        val fabSizePx = with(density) { FabSize.toPx() }
        val paddingPx = with(density) { FabPadding.toPx() }
        val maxX = (constraints.maxWidth - fabSizePx - paddingPx).coerceAtLeast(paddingPx)
        val maxY = (constraints.maxHeight - fabSizePx - paddingPx).coerceAtLeast(paddingPx)

        var offsetX by rememberSaveable { mutableFloatStateOf(UnsetPosition) }
        var offsetY by rememberSaveable { mutableFloatStateOf(UnsetPosition) }

        if (offsetX == UnsetPosition || offsetY == UnsetPosition) {
            offsetX = maxX
            offsetY = maxY
        } else {
            offsetX = offsetX.coerceIn(paddingPx, maxX)
            offsetY = offsetY.coerceIn(paddingPx, maxY)
        }

        SmallFloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .pointerInput(maxX, maxY) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX = (offsetX + dragAmount.x).coerceIn(paddingPx, maxX)
                        offsetY = (offsetY + dragAmount.y).coerceIn(paddingPx, maxY)
                    }
                },
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = stringResource(R.string.debug_fab_content_description),
            )
        }
    }
}

private val FabSize = 40.dp
private val FabPadding = 16.dp
private const val UnsetPosition = -1f
