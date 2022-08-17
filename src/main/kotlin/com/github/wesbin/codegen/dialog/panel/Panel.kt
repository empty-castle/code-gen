package com.github.wesbin.codegen.dialog.panel

import com.intellij.openapi.ui.DialogPanel

interface Panel {

    val observableProperties: ObservableProperties

    fun createPanel(): DialogPanel
}