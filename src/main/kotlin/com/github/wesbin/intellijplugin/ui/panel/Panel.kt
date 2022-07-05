package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.openapi.ui.DialogPanel

interface Panel {

    fun createPanel(): DialogPanel
}