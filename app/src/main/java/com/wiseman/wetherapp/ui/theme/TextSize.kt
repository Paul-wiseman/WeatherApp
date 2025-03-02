package com.wiseman.wetherapp.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class TextSize(
    val size50:TextUnit = 50.sp,
    val size32:TextUnit = 32.sp,
)

val LocalTextSize = compositionLocalOf { TextSize() }