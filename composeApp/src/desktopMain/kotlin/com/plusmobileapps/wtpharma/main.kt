package com.plusmobileapps.wtpharma

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "What the pharma",
    ) {
        App()
    }
}