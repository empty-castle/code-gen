package com.github.wesbin.codegen.dialog.panel

import com.intellij.openapi.ui.DialogPanel

// fixme to abstract class
interface Panel {

    fun createPanel(): DialogPanel
}