package com.github.wesbin.codegen.dialog.panel

import com.intellij.openapi.ui.DialogPanel

interface Panel {

    fun createPanel(): DialogPanel
}