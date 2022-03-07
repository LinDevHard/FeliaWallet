package com.lindevhard.felia.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color


val primaryDark = Color(0xFF751EAA)
val formsDark = Color(0xFF171227)
val backgroundDark = Color(0xFF000000)
val mainTextDark = Color(0xFFF7F7F9)
val secondaryTextDark = Color(0x73FFFFFF)

val Colors.white: Color
    get() = Color.White

val Colors.mainText: Color
    get() = mainTextDark

val Colors.secondaryText: Color
    get() = secondaryTextDark

val Colors.disabledButton: Color
    get() = if (isLight) Color(0xFFEDEDED) else Color(0xFF1F1F30)

val Colors.disabledTextColor: Color
    get() = if (isLight) Color(0xFFBCBCBC) else Color(0xFFBCBCBC)

val Colors.secondaryButton: Color
    get() = Color(0xFF2E2E43)