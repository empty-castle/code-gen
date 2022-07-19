package com.github.wesbin.intellijplugin.dialog.panel

import com.intellij.openapi.ui.DialogPanel

interface Panel {

    fun createPanel(): DialogPanel
}