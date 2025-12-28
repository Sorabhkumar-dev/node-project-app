package com.sorabh.node.ui.utils

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

enum class TaskCategory(val value: String) {
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

fun String.toTaskCategory():TaskCategory = when(this){
    TaskCategory.WORK.name -> TaskCategory.WORK
    TaskCategory.PERSONAL.name -> TaskCategory.PERSONAL
    TaskCategory.HEALTH.name -> TaskCategory.HEALTH
    TaskCategory.HOME.name -> TaskCategory.HOME
    TaskCategory.FINANCE.name -> TaskCategory.FINANCE
    TaskCategory.QUICK.name -> TaskCategory.QUICK
    TaskCategory.PROJECTS.name -> TaskCategory.PROJECTS
    TaskCategory.GOALS.name -> TaskCategory.GOALS
    TaskCategory.SHOPPING.name -> TaskCategory.SHOPPING
    TaskCategory.SPORTS.name -> TaskCategory.SPORTS
    TaskCategory.OTHER.name -> TaskCategory.OTHER
    else -> throw Exception("Invalid Task Category")
}


val TaskCategory.color: Color
    get() = when (this) {
        TaskCategory.WORK -> Color(0xFF007AFF)        // Strong Blue – focus, professionalism
        TaskCategory.PERSONAL -> Color(0xFF78716C)    // Neutral Gray – personal / misc
        TaskCategory.HEALTH -> Color(0xFFFF3B30)      // Red – health, alerts, body
        TaskCategory.HOME -> Color(0xFF34C759)        // Fresh Green – home, nature, stability
        TaskCategory.FINANCE -> Color(0xFFFFCC00)     // Gold/Yellow – money, savings
        TaskCategory.QUICK -> Color(0xFFFF9500)       // Orange – quick actions, fast tasks
        TaskCategory.PROJECTS -> Color(0xFF5856D6)    // Indigo – deep work, long-term focus
        TaskCategory.GOALS -> Color(0xFFAF52DE)       // Purple – growth, vision, ambition
        TaskCategory.SHOPPING -> Color(0xFFFF6B6B)    // Coral - Feels fun, impulsive, purchase-oriented
        TaskCategory.OTHER -> Color(0xFF6B7280)       // Black – misc
        TaskCategory.SPORTS -> Color(0xFF4A90E2)
    }

val TaskCategory.icon: DrawableResource
    get() = when(this){
        TaskCategory.WORK -> Res.drawable.ic_work
        TaskCategory.PERSONAL -> Res.drawable.ic_personal
        TaskCategory.HEALTH -> Res.drawable.ic_health
        TaskCategory.HOME -> Res.drawable.ic_home
        TaskCategory.FINANCE -> Res.drawable.ic_finance
        TaskCategory.QUICK -> Res.drawable.ic_quick
        TaskCategory.PROJECTS -> Res.drawable.ic_projects
        TaskCategory.GOALS -> Res.drawable.ic_goals
        TaskCategory.SHOPPING -> Res.drawable.ic_shopping
        TaskCategory.SPORTS -> Res.drawable.ic_sports
        TaskCategory.OTHER -> Res.drawable.ic_others
    }