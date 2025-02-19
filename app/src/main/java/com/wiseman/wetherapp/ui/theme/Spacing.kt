package com.wiseman.wetherapp.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default:Dp = 0.dp,
    val spaceExtraSmall:Dp = 4.dp,
    val spaceSmall:Dp = 8.dp,
    val space10:Dp = 10.dp,
    val spaceMedium:Dp = 16.dp,
    val spaceLarge:Dp = 32.dp,
    val spaceExtraLarge:Dp = 64.dp,
    val space200:Dp = 200.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }
