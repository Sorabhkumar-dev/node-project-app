package com.sorabh.node.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val AbsoluteWhite = Color(0xFFFFFFFF)
val AbsoluteBlack = Color(0xFF000000)

// -----------------------------------------------------------
// 2. Create the Color Scheme
// We map almost every slot to Black or White to enforce the look.
// -----------------------------------------------------------
 val BlackAndWhiteScheme = lightColorScheme(
    // Main brand color (used for Buttons, etc.)
    primary = AbsoluteBlack,
    onPrimary = AbsoluteWhite, // Text on top of primary

    // Secondary accents
    secondary = AbsoluteBlack,
    onSecondary = AbsoluteWhite,

    // Backgrounds (The critical part for your request)
    background = AbsoluteWhite,
    onBackground = AbsoluteBlack, // Text on background

    // Surface (Cards, Sheets, Menus)
    surface = AbsoluteWhite,
    onSurface = AbsoluteBlack, // Text on surface

    // Error states (keeping it monochrome)
    error = AbsoluteBlack,
    onError = AbsoluteWhite
)