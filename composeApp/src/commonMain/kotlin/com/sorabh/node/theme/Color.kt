package com.sorabh.node.theme

import androidx.compose.material3.darkColorScheme
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
    primaryContainer = Color(0xFFE5E4E2),

    // Secondary accents
    secondary = AbsoluteBlack,
    onSecondary = AbsoluteWhite,

    // Backgrounds (The critical part for your request)
    background = AbsoluteWhite,
    onBackground = AbsoluteBlack, // Text on background

    // Surface (Cards, Sheets, Menus)
    surface = AbsoluteWhite,
    onSurface = AbsoluteBlack, // Text on surface
    surfaceContainer = Color(0xFFE5E4E2).copy(0.5f),

    // Error states (keeping it monochrome)
    error = AbsoluteBlack,
    onError = AbsoluteWhite
)


val BlackAndWhiteDarkScheme = darkColorScheme(

    // Main brand color (inverse for dark mode)
    primary = AbsoluteWhite,
    onPrimary = AbsoluteBlack,
    primaryContainer = Color(0xFF2C2C2E),

    // Secondary accents
    secondary = AbsoluteWhite,
    onSecondary = AbsoluteBlack,

    // Backgrounds
    background = Color(0xFF0E0E10),      // Near-black (not pure)
    onBackground = AbsoluteWhite,

    // Surface (Cards, Sheets, Menus)
    surface = Color(0xFF161618),
    onSurface = AbsoluteWhite,
    surfaceContainer = Color(0xFF2C2C2E),

    // Error states (monochrome)
    error = AbsoluteWhite,
    onError = AbsoluteBlack
)
