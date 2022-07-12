package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.CheckBoxList
import com.intellij.ui.dsl.builder.panel

@Suppress("UnstableApiUsage")
class LeftPanel: Panel {

    override fun createPanel(): DialogPanel {
        return panel {
            row("Left") {
                CheckBoxList<String>()
                    .apply {
                        setItems(mutableListOf<String>("A", "B", "C"), null)
                    }
            }
        }
    }
}