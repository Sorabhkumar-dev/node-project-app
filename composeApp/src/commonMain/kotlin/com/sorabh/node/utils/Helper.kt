package com.sorabh.node.utils

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.caveat_bold
import node.composeapp.generated.resources.caveat_medium
import node.composeapp.generated.resources.caveat_regular
import node.composeapp.generated.resources.caveat_semi_bold
import org.jetbrains.compose.resources.Font

// 1. Define the Font Family with all weights
@Composable
fun getCaveatFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.caveat_regular, FontWeight.Normal),
        Font(Res.font.caveat_medium, FontWeight.Medium),
        Font(Res.font.caveat_semi_bold, FontWeight.SemiBold),
        Font(Res.font.caveat_bold, FontWeight.Bold)
    )
}

// 2. Define the Typography
@Composable
fun getAppTypography(): Typography {
    val caveat = getCaveatFontFamily()

    // CONFIGURATION:
    // Handwriting fonts look small. This scales them up by 15% to match system readability.
    val scale = 1.15

    // Helper to generate a TextStyle with the font and scaling applied
    fun style(
        weight: FontWeight,
        size: Int,
        lineHeight: Int,
        tracking: Double
    ): TextStyle {
        return TextStyle(
            fontFamily = caveat,
            fontWeight = weight,
            fontSize = (size * scale).sp,
            lineHeight = (lineHeight * scale).sp,
            letterSpacing = tracking.sp
        )
    }

    return Typography(
        // --- DISPLAY (Big text) ---
        displayLarge = style(FontWeight.Normal, 57, 64, -0.25),
        displayMedium = style(FontWeight.Normal, 45, 52, 0.0),
        displaySmall = style(FontWeight.Normal, 36, 44, 0.0),

        // --- HEADLINE (Headers) ---
        // I used SemiBold for Headlines so they stand out more than Body text
        headlineLarge = style(FontWeight.SemiBold, 32, 40, 0.0),
        headlineMedium = style(FontWeight.SemiBold, 28, 36, 0.0),
        headlineSmall = style(FontWeight.SemiBold, 24, 32, 0.0),

        // --- TITLE (Medium emphasis) ---
        titleLarge = style(FontWeight.Medium, 22, 28, 0.0),
        titleMedium = style(FontWeight.Medium, 16, 24, 0.15),
        titleSmall = style(FontWeight.Medium, 14, 20, 0.1),

        // --- BODY (Long text) ---
        // Normal weight for easy reading
        bodyLarge = style(FontWeight.Normal, 16, 24, 0.5),
        bodyMedium = style(FontWeight.Normal, 14, 20, 0.25),
        bodySmall = style(FontWeight.Normal, 12, 16, 0.4),

        // --- LABEL (Buttons, small UI) ---
        // Medium weight so buttons are clickable and clear
        labelLarge = style(FontWeight.Medium, 14, 20, 0.1),
        labelMedium = style(FontWeight.Medium, 12, 16, 0.5),
        labelSmall = style(FontWeight.Medium, 11, 16, 0.5)
    )
}