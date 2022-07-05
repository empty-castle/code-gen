package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel

@Suppress("UnstableApiUsage")
class RightPanel: Panel {

    override fun createPanel(): DialogPanel {
        return panel {
            row("Right") {

            }
        }
    }
}