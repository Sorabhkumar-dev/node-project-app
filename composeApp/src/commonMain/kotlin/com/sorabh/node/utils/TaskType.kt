package com.sorabh.node.utils

import androidx.compose.ui.graphics.Color
import node.composeapp.generated.resources.Res
import node.composeapp.generated.resources.ic_finance
import node.composeapp.generated.resources.ic_goals
import node.composeapp.generated.resources.ic_health
import node.composeapp.generated.resources.ic_home
import node.composeapp.generated.resources.ic_others
import node.composeapp.generated.resources.ic_personal
import node.composeapp.generated.resources.ic_projects
import node.composeapp.generated.resources.ic_quick
import node.composeapp.generated.resources.ic_shopping
import node.composeapp.generated.resources.ic_sports
import node.composeapp.generated.resources.ic_work
import org.jetbrains.compose.resources.DrawableResource

enum class TaskType(val value: String) {
    WORK("Work"),
    PERSONAL("Personal"),
    HEALTH("health"),
    HOME("Home"),
    FINANCE("Finance"),
    QUICK("Quick"),
    PROJECTS("Projects"),
    GOALS("Goals"),
    SHOPPING("Shopping"),
    SPORTS("Sports"),
    OTHER("Other")
}

val TaskType.color: Color
    get() = when (this) {
        TaskType.WORK -> Color(0xFF007AFF)        // Strong Blue – focus, professionalism
        TaskType.PERSONAL -> Color(0xFF8E8E93)    // Neutral Gray – personal / misc
        TaskType.HEALTH -> Color(0xFFFF3B30)      // Red – health, alerts, body
        TaskType.HOME -> Color(0xFF34C759)        // Fresh Green – home, nature, stability
        TaskType.FINANCE -> Color(0xFFFFCC00)     // Gold/Yellow – money, savings
        TaskType.QUICK -> Color(0xFFFF9500)       // Orange – quick actions, fast tasks
        TaskType.PROJECTS -> Color(0xFF5856D6)    // Indigo – deep work, long-term focus
        TaskType.GOALS -> Color(0xFFAF52DE)       // Purple – growth, vision, ambition
        TaskType.SHOPPING -> Color(0xFFFF6B6B)    // Coral - Feels fun, impulsive, purchase-oriented
        TaskType.OTHER -> Color(0xFF000000)        // Black – misc
        TaskType.SPORTS -> Color(0xFF4A90E2)
    }

val TaskType.icon: DrawableResource
    get() = when(this){
        TaskType.WORK -> Res.drawable.ic_work
        TaskType.PERSONAL -> Res.drawable.ic_personal
        TaskType.HEALTH -> Res.drawable.ic_health
        TaskType.HOME -> Res.drawable.ic_home
        TaskType.FINANCE -> Res.drawable.ic_finance
        TaskType.QUICK -> Res.drawable.ic_quick
        TaskType.PROJECTS -> Res.drawable.ic_projects
        TaskType.GOALS -> Res.drawable.ic_goals
        TaskType.SHOPPING -> Res.drawable.ic_shopping
        TaskType.SPORTS -> Res.drawable.ic_sports
        TaskType.OTHER -> Res.drawable.ic_others
    }